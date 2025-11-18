package com.safehouse.core.vpn

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.safehouse.core.api.NetworkClient
import com.safehouse.core.api.interceptors.SignedApisWithLocationInterceptor
import com.safehouse.core.data.Data
import com.safehouse.core.data.PaymentData
import com.safehouse.core.model.FreeVPNConnectRequest
import com.safehouse.core.model.GetMyIpResponse
import com.safehouse.core.model.GetRegionResponse
import com.safehouse.core.model.MyPlanResponse
import com.safehouse.core.model.PaymentResponse
import com.safehouse.core.model.RemovePeerRequest
import com.safehouse.core.model.SHAPIResponseCallback
import com.safehouse.core.model.UploadIpRequest
import com.safehouse.core.model.VPNConnectRequest
import com.safehouse.core.model.VPNConnectResponse
import com.safehouse.core.network.NetworkingInterface
import com.safehouse.core.util.CommonUtils
import com.safehouse.core.util.CommonUtils.parseErrorMessage
import com.safehouse.android.backend.Backend
import com.safehouse.android.backend.GoBackend
import com.safehouse.android.backend.Tunnel
import com.safehouse.config.Config
import com.safehouse.config.Interface
import com.safehouse.crypto.KeyPair
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.Date


object VPNManager {
    const val MODE_AD = "ad"
    const val MODE_VPN = "vpn"
    const val MODE_ALL = "all"
    const val MODE_NONE = ""
    var includedAppsList: ArrayList<String> = arrayListOf()
    const val statusCode = 480

    var currentVPNMode = MODE_NONE
    var lastVPNLocation = ""
    private var currentIp = ""
    private var currentMode = ""

    fun initialize(context: Context) {
        backend = GoBackend(context)
    }

    private fun generatePublicAndPrivateKey(): Pair<String, String> {
        val keyPair = KeyPair()
        return Pair(keyPair.publicKey.toBase64(), keyPair.privateKey.toBase64())
    }

//    private fun fetchSavedProfiles(context: Context) : MutableMap<String, VPNConnectResponse> {
//        val settings: SharedPreferences = context.getSharedPreferences(Data.PREFS_NAME, Context.MODE_PRIVATE)
//        val profileJson = settings.getString("savedVPNProfileData", "")
//        return Gson().fromJson(profileJson, object : TypeToken<MutableMap<String, VPNConnectResponse>>() {}.type)?: mutableMapOf()
//    }

    private fun fetchPublicKey(context: Context) : String {
        val settings: SharedPreferences = context.getSharedPreferences(Data.PREFS_NAME, Context.MODE_PRIVATE)
        val publicKey = settings.getString("publicKey", "")?: ""
        return publicKey
    }

//    private fun saveVPNProfile(context: Context, location: String, mode: String, vpnConnectResponse: VPNConnectResponse) {
//        val settings: SharedPreferences = context.getSharedPreferences(Data.PREFS_NAME, Context.MODE_PRIVATE)
//        val profileJson = settings.getString("savedVPNProfileData", "")
//        val profiles : MutableMap<String, VPNConnectResponse> = Gson().fromJson(profileJson, object : TypeToken<MutableMap<String, VPNConnectResponse>>() {}.type)?: mutableMapOf()
//
//        profiles[mode + location] = vpnConnectResponse
//
//        val editor = settings.edit()
//        editor.putString("savedVPNProfileData", Gson().toJson(profiles))
//        editor.apply()
//    }

    private fun savePublicKey(context: Context, publicKey: String) {
        val settings: SharedPreferences = context.getSharedPreferences(Data.PREFS_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString("publicKey", publicKey)
        editor.apply()
    }

//    private fun removeVPNProfile(context: Context, location: String, mode: String) {
//        val settings: SharedPreferences = context.getSharedPreferences(Data.PREFS_NAME, Context.MODE_PRIVATE)
//        val profileJson = settings.getString("savedVPNProfileData", "")
//        val profiles : MutableMap<String, VPNConnectResponse> = Gson().fromJson(profileJson, object : TypeToken<MutableMap<String, VPNConnectResponse>>() {}.type)?: mutableMapOf()
//
//        profiles.remove(mode + location)
//
//        val editor = settings.edit()
//        editor.putString("savedVPNProfileData", Gson().toJson(profiles))
//        editor.apply()
//    }

    private fun removeVPNProfile(context: Context, peer : String) {
        val settings: SharedPreferences = context.getSharedPreferences(Data.PREFS_NAME, Context.MODE_PRIVATE)
        val profileJson = settings.getString("savedVPNProfileData", "")
        val profiles : MutableMap<String, VPNConnectResponse> = Gson().fromJson(profileJson, object : TypeToken<MutableMap<String, VPNConnectResponse>>() {}.type)?: mutableMapOf()

        var keyToRemove = ""
        for(item in profiles) {
            if(item.value.peerConfig?.peersList?.get(0)?.publicKey == peer) {
                keyToRemove = item.key
                break
            }
        }
        if(keyToRemove.isNotBlank())
            profiles.remove(keyToRemove)

        val editor = settings.edit()
        editor.putString("savedVPNProfileData", Gson().toJson(profiles))
        editor.apply()
    }

    private var vpnConnectionListener : VPNConnectionListener = object : VPNConnectionListener{
        override fun onVPNConnected(
            isFreeVPN: Boolean?,
            ipAddress: String?,
            region: String?,
            displayName: String?
        ) {}
        override fun onVPNDisconnected() {}
        override fun onVPNConnectionError(errorMessage: String?, statusCode: Int) {}
    }

    // This variable is used to store the listener for payment events
    private var paymentListener: PaymentListener = object : PaymentListener {
        // This method is called when a payment is successful
        override fun onPaymentSuccess(paymentResponse: PaymentResponse?) {}
        // This method is called when a payment fails
        override fun onPaymentFailure(errorMessage: String?) {}
    }

    // This variable is used to store the listener for payment events
    private var myPlanListener: MyPlansListener = object : MyPlansListener {
        override fun onMyPlanSuccess(myPlanResponse: MyPlanResponse?) {}

        override fun onMyPlanFailure(errorMessage: String?) {}
    }

    fun registerVPNConnectionListener(vpnConnectionListener: VPNConnectionListener) {
        this.vpnConnectionListener = vpnConnectionListener
    }


    fun registerPaymentListener(paymentListener: PaymentListener) {
        this.paymentListener = paymentListener
    }

    fun registerMyPlanListener(myPlansListener: MyPlansListener) {
        this.myPlanListener = myPlansListener
    }

    fun startVPNConnection(context: Activity, location: String,includedApps: ArrayList<String> = arrayListOf()) {
        val mode = MODE_ALL
        lastVPNLocation = location
        includedAppsList = includedApps
        currentMode = "paid_vpn"
        //val savedProfiles = fetchSavedProfiles(context)

        val intentPrepare = GoBackend.VpnService.prepare(context.baseContext)
        if (intentPrepare != null) {
            context.startActivityForResult(intentPrepare, 23491)
            return
        }

        if(isTunnelUp()) {
            return
        }
//        if(savedProfiles.containsKey(mode + location)) {
//            connectWithServerAndMakeTunnelUP(context, savedProfiles[mode + location]!!, true, location, mode)
//        } else {
            val keys = generatePublicAndPrivateKey() // this creates default private key
            if (!TextUtils.isEmpty(keys.first)) {
                callVPNConnectAPI(context, location, keys.first, keys.second, mode)
          //  }
        }
    }

    private fun callVPNConnectAPI(context: Activity, location: String, publicKey: String, privateKey: String, mode: String) {
        if (!TextUtils.isEmpty(location)) {
            connectVPNTask(context, publicKey, location, privateKey, mode)
        }
    }

    private fun connectVPNTask(context: Activity, publicKey: String, region: String, privateKey: String, mode: String) {
        //saveCurrentIpAddressAndConnectVPN(context, publicKey, region, privateKey, mode)
        continueWithConnectVPN(context, "", publicKey, region, privateKey, mode)
    }

    fun startFreeVPNConnection(context: Activity, licenseType: String,email: String?,
                               phone: String, includedApps: ArrayList<String> = arrayListOf()) {
        val mode = MODE_VPN

//        lastVPNLocation = location
        includedAppsList = includedApps
        currentMode = "free_vpn"

        //val savedProfiles = fetchSavedProfiles(context)
        try {
            val intentPrepare = GoBackend.VpnService.prepare(context)
            if (intentPrepare != null) {
                context.startActivityForResult(intentPrepare, 23491)
                return
            }
        }catch (ex:SecurityException){

        }

        if(isTunnelUp()) {
            return
        }

        val keys = generatePublicAndPrivateKey() // this creates default private key
        CoroutineScope(Dispatchers.IO).launch {
            currentIp = getMyPublicIpAsync().await()
            if (!TextUtils.isEmpty(keys.first)) {
                connectFreeVPNTask(
                    context,
                    currentIp,
                    keys.first,
                    licenseType,
                    email,
                    "+91$phone",
                    keys.second,
                    mode
                )
                //  }
            }
        }
    }

    private fun connectFreeVPNTask(context: Activity,userIpAddress: String, publicKey: String, licenseType: String,email: String?,
                                   phone: String?, privateKey: String, mode: String) {
        continueWithConnectFreeVPN(context, userIpAddress, publicKey, licenseType, phone, email, privateKey, mode)
    }

    private fun saveCurrentIpAddressAndConnectVPN(
        context: Activity,
        publicKey: String,
        region: String,
        privateKey: String,
        mode: String
    ) {
        val networkClient = NetworkClient(
            false,
            context,
            NetworkingInterface.InterceptorType.SIGNED_APIS
        )
        networkClient.createService().myIp
            .enqueue(object : Callback<GetMyIpResponse> {
                override fun onResponse(call: Call<GetMyIpResponse>, response: Response<GetMyIpResponse>) {
                    if(response.isSuccessful)
                        continueWithConnectVPN(context, response.body()?.userIp?:"", publicKey, region, privateKey, mode)
                    else
                        vpnConnectionListener.onVPNConnectionError("Failed to fetch user IP",statusCode)
                }

                override fun onFailure(call: Call<GetMyIpResponse>, t: Throwable) {
                    vpnConnectionListener.onVPNConnectionError("Failed to fetch user IP",statusCode)
                }

            })
    }

    private fun continueWithConnectVPN(
        context: Activity,
        userIpAddress: String,
        publicKey: String,
        region: String,
        privateKey: String,
        mode: String
    ) {
        try {
            val networkClient = NetworkClient(
                false,
                context,
                NetworkingInterface.InterceptorType.SIGNED_APIS
            )
            //networkClient.addInterceptor(UserIPInterceptor(userIpAddress))

            networkClient.createService().connectVPN(VPNConnectRequest(publicKey, region, mode))
                .enqueue(object : Callback<VPNConnectResponse> {
                    override fun onResponse(call: Call<VPNConnectResponse>, response: Response<VPNConnectResponse>) {
                        if(response.isSuccessful) {
                            if (response.body() != null && !TextUtils.isEmpty(privateKey)) {
                                response.body()!!.privateKey = privateKey
                                //save publicKey for removePeer
                                savePublicKey(context, publicKey)
                                connectWithServerAndMakeTunnelUP(context, response.body()!!, false)
//                                val profileToSave = response.body()
//                                profileToSave?.publicKeyRequest = publicKey
//                                saveVPNProfile(context, region, mode, profileToSave!!)
                            }
                        } else {
                            // Parse error response
                            val errorBody = response.errorBody()?.string()
                            val errorResponse = parseErrorMessage(errorBody)
                            val errorMessage = errorResponse?.getFormattedMessage() ?: "Unknown error"
                            val statusCode = errorResponse?.statusCode ?: response.code()

                            vpnConnectionListener.onVPNConnectionError(errorMessage, statusCode)
                            }
                    }

                    override fun onFailure(call: Call<VPNConnectResponse>, t: Throwable) {
                        vpnConnectionListener.onVPNConnectionError("Failed to fetch Peer",statusCode)
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
            vpnConnectionListener.onVPNConnectionError("Failed to fetch Peer",statusCode)
        }
    }

    private fun continueWithConnectFreeVPN(
        context: Activity,
        userIpAddress: String,
        publicKey: String,
        licenseType: String,
        phone: String?,
        email: String?,
        privateKey: String,
        mode:String
    ) {
        try {
            val networkClient = NetworkClient(
                false,
                context,
                NetworkingInterface.InterceptorType.LOGIN_EXTERNAL
            )
            //adding a user ip
//            networkClient.addInterceptor(UserIPInterceptor(userIpAddress))

            networkClient.createService().connectFreeVPN(FreeVPNConnectRequest(publicKey,licenseType,phone = phone,email, mode,userIpAddress))
                .enqueue(object : Callback<VPNConnectResponse> {
                    override fun onResponse(call: Call<VPNConnectResponse>, response: Response<VPNConnectResponse>) {
                        if(response.isSuccessful) {
                            if (response.body() != null && !TextUtils.isEmpty(privateKey)) {
                                response.body()!!.privateKey = privateKey
                                lastVPNLocation = response.body()?.region ?: ""
                                //save publicKey for removePeer
                                savePublicKey(context, publicKey)
                                connectWithServerAndMakeTunnelUP(context, response.body()!!, false, licenseType = licenseType)
//                                val profileToSave = response.body()
//                                profileToSave?.publicKeyRequest = publicKey
//                                saveVPNProfile(context, region, mode, profileToSave!!)
                            }
                        } else
                            vpnConnectionListener.onVPNConnectionError("Failed to fetch Peer",statusCode)
                    }

                    override fun onFailure(call: Call<VPNConnectResponse>, t: Throwable) {
                        vpnConnectionListener.onVPNConnectionError("Failed to fetch Peer",statusCode)
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
            vpnConnectionListener.onVPNConnectionError("Failed to fetch Peer",statusCode)
        }
    }

    var RxCheckCountdown = 5
    private fun testNewTunnel(
        activity: Activity,
        isFromSavedProfile: Boolean,
        location: String? = null,
        mode: String? = null,
        isNewCheck: Boolean = false,
        licenseType: String?= null,
        result: VPNConnectResponse
    ) {
        if(isNewCheck)
            RxCheckCountdown = 5
        else
            --RxCheckCountdown
        if(RxCheckCountdown > 0) {
            CoroutineScope(Dispatchers.IO).launch {
                val allowedTypes = listOf("free_vpn", "ads_vpn")
                delay(1000)
                if((getConnectionStats()?.rxBytes ?: 0) == 0L)
                    testNewTunnel(activity, isFromSavedProfile, location, mode, result = result)
                else {
                    afterIp = (result.peerConfig?.peersList?.firstOrNull()?.endPoint?.let {
                        CommonUtils.extractIpAddress(it)
                    }).toString()
                    CoroutineScope(Dispatchers.IO).launch {
                        if (licenseType !in allowedTypes) {
                            uploadIpData(activity)
                        }
                    }
                    currentVPNMode = mode ?: MODE_NONE
//                    Log.d("IPcap", "before:$beforeIp after:$afterIp")
                    if (currentMode == "free_vpn") {
                        vpnConnectionListener.onVPNConnected(true, afterIp, "${result.region}", "${result.displayName}")
                    } else{
                        vpnConnectionListener.onVPNConnected(false,null,null,null)
                    }
                }
            }
        } else {
            disconnectTunnel(activity)
            if(location != null && mode != null) {
                //removeVPNProfile(activity, location, mode)
                startVPNConnection(activity, location)
            } else
                vpnConnectionListener.onVPNConnectionError("VPN Connection Failed",statusCode)
        }
    }

    private lateinit var backend: Backend
    private var wgTunnel = WgTunnel()
    private var beforeIp = ""
    private var afterIp = ""

    private fun connectTunnel(activity: Activity, config: Config, isFromSavedProfile: Boolean,
                              location: String? = null, mode: String? = null, licenseType: String?= null,
                              result: VPNConnectResponse) {

        AsyncTask.execute {
            try {
                //val tunnelManager = TunnelManager(activity,FileConfigStore(activity))
                var updatedConfig: Config? = null
                if(includedAppsList.isNotEmpty()) {
                    config.let { oldConfig ->
                        val updatedIncludedApps =
                            ArrayList(oldConfig.`interface`.includedApplications)
                        updatedIncludedApps.removeAll(includedAppsList.toSet())
                        updatedIncludedApps.addAll(includedAppsList)
                        val newInterface = Interface.Builder()
                            .addAddresses(oldConfig.`interface`.addresses)
                            .setMtu(oldConfig.`interface`.mtu.orElse(0))
                            .setListenPort(oldConfig.`interface`.listenPort.orElse(0))
                            .setKeyPair(oldConfig.`interface`.keyPair)
                            .addDnsServers(oldConfig.`interface`.dnsServers)
                            .includeApplications(updatedIncludedApps)
                            .build()

                        updatedConfig = Config.Builder()
                            .setInterface(newInterface)
                            .addPeers(oldConfig.peers)
                            .build()
                    }
                }


                val state = backend.setState(wgTunnel, Tunnel.State.UP, if(mode!= MODE_AD) updatedConfig?: config else config)

                if (state == Tunnel.State.UP) {
                    val allowedTypes = listOf("free_vpn", "ads_vpn")
                    afterIp = (result.peerConfig?.peersList?.firstOrNull()?.endPoint?.let {
                        CommonUtils.extractIpAddress(it)
                    }).toString()
                    CoroutineScope(Dispatchers.IO).launch {
                        if (licenseType !in allowedTypes) {
                            uploadIpData(activity)
                        }
                    }
//                    Log.d("IPcap", "before:$beforeIp after:$afterIp")

                    if (currentMode in allowedTypes) {
                        vpnConnectionListener.onVPNConnected(
                            true,
                            afterIp,
                            "${result.region}",
                            "${result.displayName}"
                        )
                    } else {
                        vpnConnectionListener.onVPNConnected(false, afterIp, null, null)
                    }
                }
                    //testNewTunnel(activity, isFromSavedProfile, location, mode, true,licenseType,result)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun disconnectTunnel(context: Context) {
        AsyncTask.execute {
            try {
                removePeer(context)
                backend.setState(wgTunnel, Tunnel.State.DOWN, null)
                vpnConnectionListener.onVPNDisconnected()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun removePeer(context: Context) {
        val mode = currentVPNMode
        val location = lastVPNLocation
        //val savedProfiles = fetchSavedProfiles(context)
        //val vpnProfile = savedProfiles[mode + location]
        val networkClient = NetworkClient(
            false,
            context,
            NetworkingInterface.InterceptorType.LOGIN_EXTERNAL
        )

//        Log.d("vpnmode", "mode : $mode, location: $location")
        //Log.d("vpnmode", Gson().toJson(vpnProfile))
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            networkClient.createService().removePeerConfig(
                RemovePeerRequest(
                fetchPublicKey(context),
                lastVPNLocation
            )
            ).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    //if(response.isSuccessful)
                        //removeVPNProfile(context, location, mode)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
            })
        }
    }

    fun disconnectFreeTunnel(context: Context) {
        AsyncTask.execute {
            try {
                removeFreePeer(context)
                backend.setState(wgTunnel, Tunnel.State.DOWN, null)
                vpnConnectionListener.onVPNDisconnected()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun removeFreePeer(context: Context) {
        val mode = currentVPNMode
        val location = lastVPNLocation
        //val savedProfiles = fetchSavedProfiles(context)
        //val vpnProfile = savedProfiles[mode + location]
        val networkClient = NetworkClient(
            false,
            context,
            NetworkingInterface.InterceptorType.LOGIN_EXTERNAL
        )

//        Log.d("vpnmode", "mode : $mode, location: $location")
        //Log.d("vpnmode", Gson().toJson(vpnProfile))
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            networkClient.createService().removeFreePeerConfig(
                RemovePeerRequest(
                    fetchPublicKey(context),
                    lastVPNLocation
                )
            ).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    //if(response.isSuccessful)
                    //removeVPNProfile(context, location, mode)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
            })
        }
    }

    fun isTunnelUp() : Boolean {
        return try {
            backend.runningTunnelNames.contains(wgTunnel.name)
        }catch (e:Exception){
            false
        }
    }

    fun getConnectionStats() : SafehouseVPNStatistics? {
        return try {
            val stats = backend.getStatistics(wgTunnel)
            val peerStats = stats.peer(stats.peers().first())
            SafehouseVPNStatistics(
                peerStats?.rxBytes?:0,
                peerStats?.txBytes?:0,
                peerStats?.latestHandshakeEpochMillis?:0
            )
        } catch (e : Exception) {
            null
        }
    }

    fun getCurrentPeer(): String? {
        return try {
            val stats = backend.getStatistics(wgTunnel)
            return stats.peers().first().toBase64()
        } catch (e : Exception) {
            null
        }
    }

    fun checkLastHanshake(context: Context) : Boolean{
        if(isTunnelUp()) {
            val lastHandshake = getConnectionStats()?.lastHandShakeEpoch?:0
            if(lastHandshake > 0 && Date().time - lastHandshake > 1000 * 60 * 60 * 20) {
                removeVPNProfile(context, getCurrentPeer()?:"")
                return false
            }
        }
        return true
    }

    private fun getConfigFromVPNConnectResponse(result: VPNConnectResponse) : Config {
        val data =
            """
            [Interface]
            Address = ${result.peerConfig?.interfaceResponse?.addressList?.let { TextUtils.join(",", it) }}
            PrivateKey = ${result.privateKey}
            DNS = ${result.peerConfig?.interfaceResponse?.dnsList?.let { TextUtils.join(",", it) }}

            [Peer]
            PublicKey = ${result.peerConfig?.peersList?.get(0)?.publicKey}
            AllowedIPs = ${result.peerConfig?.peersList?.get(0)?.allowedIPs?.let { TextUtils.join(",", it) }}
            Endpoint = ${result.peerConfig?.peersList?.get(0)?.endPoint}
            PersistentKeepalive = ${result.peerConfig?.peersList?.get(0)?.persistentKeepAlive}
            """
                .trimIndent()

        return parse(ByteArrayInputStream(data.toByteArray(StandardCharsets.UTF_8)))
    }

    private fun connectWithServerAndMakeTunnelUP(
        context: Activity,
        result: VPNConnectResponse,
        isFromSavedProfile: Boolean,
        location: String? = null,
        mode: String? = null,
        licenseType: String? = null,
    ){
        CoroutineScope(Dispatchers.IO).launch{
            beforeIp = getMyPublicIpAsync().await()
            connectTunnel(context, getConfigFromVPNConnectResponse(result), isFromSavedProfile, location, mode, licenseType,result)
        }
    }

    private fun parse(stream: InputStream?): Config {
        requireNotNull(stream)
        return Config.parse(BufferedReader(InputStreamReader(stream)))
    }

    fun getVPNRegions(context: Context, latitude : String, longitude : String, shapiResponseCallback: SHAPIResponseCallback<List<GetRegionResponse>>) {
        SignedApisWithLocationInterceptor.location = "$latitude, $longitude"
        val networkClient = NetworkClient(
            false,
            context,
            NetworkingInterface.InterceptorType.SIGNED_APIS_WITH_LOCATION
        )
        networkClient.createService().region
            .enqueue(object : Callback<List<GetRegionResponse>> {
                override fun onResponse(call: Call<List<GetRegionResponse>>, response: Response<List<GetRegionResponse>>) {
                    shapiResponseCallback.onResponseSuccess(response.body()?: listOf())
                }

                override fun onFailure(call: Call<List<GetRegionResponse>>, t: Throwable) {
                    shapiResponseCallback.onResponseFailure("Error ${t.message}")
                }
            })
    }

    fun getVPNRegions(context: Context, shapiResponseCallback: SHAPIResponseCallback<List<GetRegionResponse>>){
        getVPNRegions(context, "23.027298", "78.007217", shapiResponseCallback)
    }

    private fun uploadIpData(context: Context) {
        val networkClient = NetworkClient(
            false,
            context,
            NetworkingInterface.InterceptorType.SIGNED_APIS
        )
        networkClient.createService().uploadIpData(UploadIpRequest(beforeIp, afterIp))
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
            })
    }

    private suspend fun getMyPublicIpAsync() : Deferred<String> =
        coroutineScope {
            async(Dispatchers.IO) {
                var result = ""
                result = try {
                    val url = URL("https://api.ipify.org")
                    val httpsURLConnection = url.openConnection()
                    val iStream = httpsURLConnection.getInputStream()
                    val buff = ByteArray(1024)
                    val read = iStream.read(buff)
                    String(buff,0, read)
                } catch (e: Exception) {
                    "error : $e"
                }
                return@async result
            }
        }


    fun getPaymentLink(context: Context,email: String?,phone: String?,productId: String?) {
        val networkClient = NetworkClient(
            false,
            context,
            NetworkingInterface.InterceptorType.LOGIN_EXTERNAL
        )
        networkClient.createService().getPaymentLink(PaymentData(email, phone, productId))
            .enqueue(object : Callback<PaymentResponse> {
                override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {

                    if (response.isSuccessful){
                        paymentListener.onPaymentSuccess(response.body())
                    }
                    else{
                        paymentListener.onPaymentFailure("Something went wrong. Please try again later.")
                    }
                }
                override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {

                    paymentListener.onPaymentFailure(t.localizedMessage)
                }
            })
    }

    fun getPlans(context: Context) {
        val networkClient = NetworkClient(
            false,
            context,
            NetworkingInterface.InterceptorType.SIGNED_APIS
        )

        CoroutineScope(Dispatchers.IO).launch {
            networkClient.createService().getPlans().enqueue(object : Callback<MyPlanResponse> {
                override fun onResponse(
                    call: Call<MyPlanResponse>,
                    response: Response<MyPlanResponse>
                ) {
                    myPlanListener.onMyPlanSuccess(response.body())
                }
                override fun onFailure(call: Call<MyPlanResponse>, t: Throwable) {
                    myPlanListener.onMyPlanFailure(t.localizedMessage)
                }
            })
        }
    }
}