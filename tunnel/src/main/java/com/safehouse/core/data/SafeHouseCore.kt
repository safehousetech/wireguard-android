package com.safehouse.core.data

import android.content.Context
import com.safehouse.core.network.NetworkingInterface
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SafeHouseCore @Inject constructor(
    var dataManager : DataManager? = null,
    var data : Data? = null,
    var tokenManager : TokenManager? = null,
    var networkingInterface : NetworkingInterface? = null,
) {


    fun initialize(appContext : Context) {
       // dataManager = AppDataManager(appContext)
    }
}