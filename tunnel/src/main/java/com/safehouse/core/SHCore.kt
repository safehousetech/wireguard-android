package com.safehouse.core

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.safehouse.core.api.NetworkClient
import com.safehouse.core.data.AppDataManager
import com.safehouse.core.data.Data
import com.safehouse.core.data.DataManager
import com.safehouse.core.data.TokenManager
import com.safehouse.core.model.Login3rdPartyRequest
import com.safehouse.core.model.LoginExternalResponse
import com.safehouse.core.model.SHAPIResponseCallback
import com.safehouse.core.network.NetworkingInterface
import com.safehouse.core.util.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SHCore(context: Context) {
    companion object {
        var instance : SHCore? = null

        fun getInstance(context : Context) : SHCore {
            if(instance != null)
                return instance!!
            return SHCore(context)
        }
    }

    var dataManager: DataManager
    val tokenManager = TokenManager(context)

    init {
        this.dataManager = AppDataManager(
            context,
            Config(context),
            Data.getInstance(context),
            tokenManager
        )
    }

    @SuppressLint("HardwareIds")
    fun login(context: Context, phone: String, shapiResponseCallback: SHAPIResponseCallback<LoginExternalResponse?>) {
        val networkClient = NetworkClient(
            false,
            context,
            NetworkingInterface.InterceptorType.LOGIN_EXTERNAL
        )
        val androidId: String = android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID)
        networkClient.createService().callLoginExternal(Login3rdPartyRequest(
            "+91$phone",
            androidId,
            Build.MODEL,
            "android"
        )).enqueue(object : Callback<LoginExternalResponse> {
            override fun onResponse(call: Call<LoginExternalResponse>, response: Response<LoginExternalResponse>) {
                if(response.isSuccessful) {
                    tokenManager.token = response.body()?.token ?: ""
                    tokenManager.refreshToken = response.body()?.refreshToken ?: ""
                    shapiResponseCallback.onResponseSuccess(response.body())
                } else
                    shapiResponseCallback.onResponseFailure("${response.code()} ${response.message()}")
            }

            override fun onFailure(call: Call<LoginExternalResponse>, t: Throwable) {
                shapiResponseCallback.onResponseFailure("Error :${t.message}")
            }
        })
    }
}