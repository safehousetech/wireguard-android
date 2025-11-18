package com.safehouse.core.network

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import com.safehouse.core.api.ApiResponse
import com.safehouse.core.api.NetworkClient
import com.safehouse.core.api.Requester.GetAllowedDataListRequester
import com.safehouse.core.api.Requester.LoginRequesterV2
import com.safehouse.core.api.Requester.RefreshTokenRequester
import com.safehouse.core.api.Requester.SelectLicenseRequester
import com.safehouse.core.api.Requester.VPNConnectRequester
import com.safehouse.core.data.AppDataManager
import com.safehouse.core.data.Data
import com.safehouse.core.model.AppLocationRequest
import com.safehouse.core.model.AppLocationResponse
import com.safehouse.core.model.GetExcludeAppResponse
import com.safehouse.core.model.GetLicenseResponse
import com.safehouse.core.model.GetMyIpResponse
import com.safehouse.core.model.GetRegionResponse
import com.safehouse.core.model.InstalledApp
import com.safehouse.core.model.LoginRequestObj
import com.safehouse.core.model.LoginResponseObj
import com.safehouse.core.model.PawnedModel
import com.safehouse.core.model.ReIssueTokenRequestV2
import com.safehouse.core.model.RefreshTokenResponse
import com.safehouse.core.model.ResponseBaseModelClass
import com.safehouse.core.model.UpdateTransactionRequest
import com.safehouse.core.model.UpdateTransactionResponse
import com.safehouse.core.model.VPNConnectRequest
import com.safehouse.core.model.VPNConnectResponse
import com.safehouse.core.model.ValidateRequest
import com.safehouse.core.model.ValidateResponse
import com.safehouse.core.model.apimodels.AuthenticateModel
import com.safehouse.core.model.apimodels.DeviceAuthenticateResponseModel
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class NetworkDataManager @Inject constructor(
    val appContext: Context,
    val data: Data,
    val appDataManager: AppDataManager,
    val getAllowedDataListRequester: GetAllowedDataListRequester,
    val loginRequesterV2: LoginRequesterV2,
    val vpnConnectRequester: VPNConnectRequester,
    @Named("Retrofit.masterApiCalls") val retrofitMasterApiInterface: com.safehouse.core.api.MasterApiInterface
) : NetworkingInterface {


    private val TAG = "NetworkDataManager"

    //used for MasterApiCalls header
    companion object {
        const val contentTypeJSON = "application/json"
        const val updated_breach_endpoint = "api/v1/breach/check-email?email="

        @Throws(UnsupportedEncodingException::class)
        fun getPostDataString(params: java.util.HashMap<String, String>): String? {
            val result = java.lang.StringBuilder()
            var first = true
            for ((key, value) in params) {
                if (first) first = false else result.append("&")
                Log.e("TTTTTTTT", "$key=$value")
                result.append(URLEncoder.encode(key, "UTF-8"))
                result.append("=")
                result.append(URLEncoder.encode(value, "UTF-8"))
            }
            return result.toString()
        }

    }

    @SuppressLint("HardwareIds")
    val androidId: String = android.provider.Settings.Secure.getString(
        appContext.contentResolver,
        android.provider.Settings.Secure.ANDROID_ID
    )

    override fun callReissueTokenApiV2(
        isMoreTimeoutNeeded: Boolean?,
        email: String?,
        phone: String?,
        alternateEmail: String?,
        alternatePhone: String?,
        appName: String?,
        customerId: String?,
        deepLinkSource: String?,
        licenses: ArrayList<String?>?,
        id: String?,
        firstName: String?,
        otp: String?
    ): com.safehouse.core.api.ApiResponse<com.safehouse.core.model.ReissueTokenResponse?>? {
        try {
            val reIssueTokenRequest = ReIssueTokenRequestV2(
                androidId,
                Build.MODEL,
                "android",
                otp,
                email,
                phone,
                alternateEmail,
                alternatePhone,
                deepLinkSource,
                appName,
                customerId
            )
            val reIssueTokenRequester =
                com.safehouse.core.api.Requester.ReIssueTokenRequesterV2(
                    com.safehouse.core.api.NetworkClient(
                        true,
                        appContext,
                        NetworkingInterface.InterceptorType.REISSUE_TOKEN
                    ), reIssueTokenRequest
                )
//            reIssueTokenRequester.setDynamicTokenHeader(data.dynamicLinkToken)
            return reIssueTokenRequester.executeRequest(isMoreTimeoutNeeded)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun authenticateAndCallGetLicenseApi(isMoreTimeoutNeeded: Boolean): com.safehouse.core.api.ApiResponse<GetLicenseResponse> {
        try {
            val getLicenseRequester = com.safehouse.core.api.Requester.GetLicenseRequester(
                com.safehouse.core.api.NetworkClient( true, appContext, NetworkingInterface.InterceptorType.SIGNED_APIS
                )
            )
            return getLicenseRequester.executeRequest(isMoreTimeoutNeeded)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return com.safehouse.core.api.ApiResponse(
            com.safehouse.core.api.ApiResponse.Status.ERROR,
            null,
            "ERROR",
            404
        )
    }

    override fun callConnectVpnApi(
        ip: String,
        publicKey: String?,
        region: String?,
        mode: String
    ): ApiResponse<VPNConnectResponse?>? {
        try {
            val vpnConnectRequest = VPNConnectRequest(publicKey, region, mode)
            vpnConnectRequester.setVpnConnectRequest(ip, vpnConnectRequest)
            return vpnConnectRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return com.safehouse.core.api.ApiResponse(
            com.safehouse.core.api.ApiResponse.Status.ERROR,
            null,
            "ERROR",
            404
        )
    }

    override fun authenticateAndCallGetRegionsApi(): com.safehouse.core.api.ApiResponse<List<GetRegionResponse>> {
        return try {
            val getRegionRequester = com.safehouse.core.api.Requester.GetRegionRequester(
                NetworkClient(
                    false,
                    appContext,
                    NetworkingInterface.InterceptorType.SIGNED_APIS_WITH_LOCATION
                )
            )
            getRegionRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
            com.safehouse.core.api.ApiResponse(
                com.safehouse.core.api.ApiResponse.Status.ERROR,
                null,
                "ERROR",
                404
            )
        }
    }

    override fun callValidateUserApi(isMoreTimeoutNeeded: Boolean): com.safehouse.core.api.ApiResponse<ValidateResponse> {
        return try {
            val fcmToken = appDataManager.savedFcmToken
            val validateRequest =
                ValidateRequest(
                    "1",
                    ValidateRequest.Params(androidId, fcmToken),
                    "validate",
                    "2.0"
                )
            val validateRequester =
                com.safehouse.core.api.Requester.ValidateRequester(
                    com.safehouse.core.api.NetworkClient(
                        true,
                        appContext,
                        NetworkingInterface.InterceptorType.SIGNED_APIS
                    ), validateRequest
                )
            validateRequester.executeRequest(isMoreTimeoutNeeded)
        } catch (e: Exception) {
            e.printStackTrace()
            com.safehouse.core.api.ApiResponse(
                com.safehouse.core.api.ApiResponse.Status.ERROR,
                null,
                "ERROR",
                404
            )
        }
    }

    override fun callLoginApiV2(
        user: String?,
        isMoreTimeoutNeeded: Boolean?,
        isPhoneNumberLogin: Boolean?
    ): ApiResponse<LoginResponseObj>? {
        try {
            var phone: String? = null
            var email: String? = null
            if (isPhoneNumberLogin != null) {
                if (isPhoneNumberLogin) {
                    phone = user
                } else {
                    email = user
                }
            }
            val loginRequest = LoginRequestObj(phone, email)
            loginRequesterV2.setLoginRequest(loginRequest)
            return loginRequesterV2.executeRequest(isMoreTimeoutNeeded)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun getAllowedApps(isMoreTimeoutNeeded: Boolean?): ApiResponse<List<GetExcludeAppResponse>> {
        return try {
            getAllowedDataListRequester.executeRequest(isMoreTimeoutNeeded)
        } catch (e: Exception) {
            e.printStackTrace()
            com.safehouse.core.api.ApiResponse(
                com.safehouse.core.api.ApiResponse.Status.ERROR,
                null,
                "ERROR",
                404
            )
        }
    }

    override fun getRefreshTokenApi():ApiResponse<RefreshTokenResponse?>? {
        return try {
            val refreshTokenRequester =
                RefreshTokenRequester(
                    NetworkClient(
                        false,
                        appContext,
                        NetworkingInterface.InterceptorType.REFRESH_TOKEN
                    )
                )
            refreshTokenRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun getRefreshTokenApiForUserInstalledApps(mData: Data): com.safehouse.core.api.ApiResponse<RefreshTokenResponse?>? {
        return try {
            val refreshTokenRequester =
                com.safehouse.core.api.Requester.RefreshTokenRequester(
                    com.safehouse.core.api.NetworkClient(
                        false,
                        appContext,
                        NetworkingInterface.InterceptorType.REFRESH_TOKEN
                    )
                )
            refreshTokenRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override fun callAttachLicenseApi(licenseHash: String): com.safehouse.core.api.ApiResponse<ResponseBody>? {
        try {
            val attachLicenseRequester = com.safehouse.core.api.Requester.AttachLicenseRequester(
                com.safehouse.core.api.NetworkClient(
                    false,
                    appContext,
                    NetworkingInterface.InterceptorType.SIGNED_APIS
                )
            )
            attachLicenseRequester.setLicenseHash(licenseHash)
            return attachLicenseRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    override fun logout(baseServerUrl: String?, appCode: String?): String? {
        try {
            val url = URL("$baseServerUrl/apiv1/logout/")
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ")
            urlConnection.setRequestProperty("Accept", "*/*")
            urlConnection.requestMethod = "POST"
            urlConnection.doOutput = true
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            val params = HashMap<String, String>()
            params["user"] = if (data.email != null) data.email else data.phoneNumber
            params["pass"] = data.password
            params["app"] = appCode.toString()
            params["device"] = androidId
            return try {
                val os = urlConnection.outputStream
                val writer = BufferedWriter(
                    OutputStreamWriter(os, "UTF-8")
                )
                writer.write(getPostDataString(params))
                writer.flush()
                writer.close()
                os.close()
                val status = urlConnection.responseCode
                when (status) {
                    HttpURLConnection.HTTP_OK -> "Success"
                    401 -> "401"
                    else -> {
                        val bufferedReader =
                            BufferedReader(InputStreamReader(urlConnection.errorStream))
                        val stringBuilder = StringBuilder()
                        var line: String?
                        while (bufferedReader.readLine().also { line = it } != null) {
                            stringBuilder.append(line).append("\n")
                        }
                        bufferedReader.close()
                        stringBuilder.toString()
                    }
                }
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

//    override fun callBreachAPI(baseURL:String,mail: String?, breachResponse: com.safehouse.core.api.BreachApiResponseV2?) {
//        val url = baseURL + updated_breach_endpoint + mail
//
//
//        val jsonArrayRequest = JsonArrayRequest(
//            Request.Method.GET, url, null,
//            { response: JSONArray ->
//                try {
//                    val arr: JSONArray = response
//                    val tmp = ArrayList<PawnedModel>()
//                    for (i in 0 until arr.length()) {
//                        tmp.add(PawnedModel.updatedJsonObject(arr.getJSONObject(i)))
//                    }
//                    breachResponse?.onSuccess(tmp.size)
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }
//        ) {
//            breachResponse?.onError()
//        }
//        volleyRequestQueue.add(jsonArrayRequest)
//    }

    override fun callAppLocationApi(packagesArray: List<String>): ApiResponse<AppLocationResponse>? {
        try {
            val appLocationRequest = AppLocationRequest("android", packagesArray)
            val appLocationRequester = com.safehouse.core.api.Requester.GetAppLocationRequester(
                com.safehouse.core.api.NetworkClient( false,
                    appContext, NetworkingInterface.InterceptorType.SIGNED_APIS
                ),
                appLocationRequest
            )
            return appLocationRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return com.safehouse.core.api.ApiResponse(
            com.safehouse.core.api.ApiResponse.Status.ERROR,
            null,
            "ERROR",
            404
        )
    }

    //master api calls

    override fun postAntiTheftInfo(
        authHeader: String,
        lostDeviceDTO: com.safehouse.core.model.LostDeviceDTO
    ): com.safehouse.core.model.ResponseBaseModelClass<Long> {
        var res = ResponseBaseModelClass<Long>()
        try {
            res =
                retrofitMasterApiInterface.postAntiTheft(contentTypeJSON, authHeader, lostDeviceDTO)
                    .execute()
                    .body()!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return res
    }

    override fun logoutFromDevice(
        authHeader: String,
        deviceId: Int,
        status: Int
    ): ResponseBaseModelClass<com.safehouse.core.model.apimodels.LogoutDeviceResponse> {
        var res = ResponseBaseModelClass<com.safehouse.core.model.apimodels.LogoutDeviceResponse>()
        try {
            res =
                retrofitMasterApiInterface.deviceLogout(
                    contentTypeJSON,
                    authHeader,
                    deviceId,
                    status
                ).execute()
                    .body()!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return res
    }

    override fun deviceAuth(authenticateModel: AuthenticateModel): ResponseBaseModelClass<DeviceAuthenticateResponseModel> {
        var res =
            ResponseBaseModelClass<DeviceAuthenticateResponseModel>()
        try {
            res = retrofitMasterApiInterface.deviceAuthenticate(contentTypeJSON, authenticateModel)
                .execute()
                .body()!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return res
    }

    //ApiUtility
    override fun licenseSelection(
        mData: Data,
        licenseHash: String,
        getApplicationContext: Context
    ): com.safehouse.core.api.ApiResponse<ResponseBody> {
        try {
            val selectLicenseRequester = com.safehouse.core.api.Requester.SelectLicenseRequester(
                com.safehouse.core.api.NetworkClient(
                    false,
                    getApplicationContext,
                    NetworkingInterface.InterceptorType.SIGNED_APIS
                ),
                licenseHash
            )
            return selectLicenseRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return com.safehouse.core.api.ApiResponse<ResponseBody>(
            com.safehouse.core.api.ApiResponse.Status.ERROR,
            null,
            "ERROR",
            404
        )
    }


    override fun logoutUser(
        mData: Data?,
        getApplicationContext: Context?
    ): com.safehouse.core.api.ApiResponse<ResponseBody>? {
        try {
            val logoutRequester =
                com.safehouse.core.api.Requester.LogoutRequester(
                    com.safehouse.core.api.NetworkClient(
                        false,
                        getApplicationContext,
                        NetworkingInterface.InterceptorType.SIGNED_APIS
                    ), getApplicationContext,
                    mData?.getStringData(Data.DEVICE_SERVER_ID) ?: ""
                )
            return logoutRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun AuthenticateAndGetLicense(mData: Data?): com.safehouse.core.api.ApiResponse<GetLicenseResponse?>? {
        try {
            val getLicenseRequester = com.safehouse.core.api.Requester.GetLicenseRequester(
                com.safehouse.core.api.NetworkClient(
                    true,
                    appContext,
                    NetworkingInterface.InterceptorType.SIGNED_APIS
                )
            )
            return getLicenseRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return com.safehouse.core.api.ApiResponse(
            com.safehouse.core.api.ApiResponse.Status.ERROR,
            null,
            "ERROR",
            404
        )
    }

    //mData.licenseHash should not be sent for Extending...

    override fun updateLicence(
        licenseLength: String?,
        licenseType: String?,
        androidId: String?,
        mData: Data,
        mReciept: UpdateTransactionRequest.Receipt?,
        isUpgrade: Boolean
    ): ApiResponse<UpdateTransactionResponse?>? {
        try {
            if (mReciept != null) {
                val updateTransactionRequest = UpdateTransactionRequest(
                    isUpgrade = isUpgrade,
                    deviceId = androidId,
                    licenseLength = licenseLength?:"",
                    licenseType = licenseType?:"",
                    license = mData.licenseHash,
                    receipt = mReciept
                )
                val updateTransactionRequester = com.safehouse.core.api.Requester.UpdateTransactionRequester(
                    NetworkClient(
                        false,
                        appContext,
                        NetworkingInterface.InterceptorType.SIGNED_APIS
                    ), updateTransactionRequest
                )
                return updateTransactionRequester.executeRequest(true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ApiResponse(
            ApiResponse.Status.ERROR,
            null,
            "ERROR",
            404
        )
    }

//    @AddTrace(name = "validateUserAPIcalled")
    override fun validateUser(data: Data): ApiResponse<ValidateResponse?>? {
        return try {
            val fcmToken = appDataManager.savedFcmToken
            val validateRequest = ValidateRequest(
                "1",
                ValidateRequest.Params(androidId, fcmToken),
                "validate",
                "2.0"
            )
            val validateRequester = com.safehouse.core.api.Requester.ValidateRequester(
                NetworkClient(
                    true,
                    appContext,
                    NetworkingInterface.InterceptorType.SIGNED_APIS
                ), validateRequest
            )
            validateRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(
                com.safehouse.core.api.ApiResponse.Status.ERROR,
                null,
                "ERROR",
                404
            )
        }
    }

    override fun licenseSelection(
        mData: Data,
        licenseHash: String
    ): ApiResponse<ResponseBody> {
        try {
            val selectLicenseRequester = SelectLicenseRequester(
                NetworkClient(
                    false,
                    appContext,
                    NetworkingInterface.InterceptorType.SIGNED_APIS
                ), licenseHash
            )
            return selectLicenseRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ApiResponse(
            com.safehouse.core.api.ApiResponse.Status.ERROR,
            null,
            "ERROR",
            404
        )
    }


    override fun getExcludeAppsRequest(
        mData: Data,
        mPackages: MutableList<InstalledApp>?
    ): ApiResponse<AppLocationResponse>? {
        try {
            val appLocationRequest = AppLocationRequest(
                "android",
                mPackages?.map { it.packageName ?: "" }?.toList() ?: listOf()
            )
            val getExcludeAppsRequester =
                com.safehouse.core.api.Requester.GetAppLocationRequester(
                   NetworkClient(
                        false,
                        appContext,
                        NetworkingInterface.InterceptorType.SIGNED_APIS
                    ), appLocationRequest
                )
            return getExcludeAppsRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return com.safehouse.core.api.ApiResponse(
            com.safehouse.core.api.ApiResponse.Status.ERROR,
            null,
            "ERROR",
            404
        )
    }

    override fun getIpAddressFromServer(): ApiResponse<GetMyIpResponse> {
        return try {
            val getMyIpRequester = com.safehouse.core.api.Requester.GetMyIpRequester(
                NetworkClient(false, appContext, NetworkingInterface.InterceptorType.SIGNED_APIS)
            )
            getMyIpRequester.executeRequest(true)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(
                com.safehouse.core.api.ApiResponse.Status.ERROR,
                null,
                "ERROR",
                404
            )
        }
    }
}