package com.safehouse.core.di

import android.app.Activity
import com.safehouse.core.SHCore
import com.safehouse.core.api.NetworkClient
import com.safehouse.core.data.DataManager
import com.safehouse.core.network.NetworkingInterface
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [
        SafehouseCoreModule::class
    ]
)
interface SafehouseCoreComponent {

    fun inject(networkClient: NetworkClient)
    fun inject(shCore: SHCore)
    fun inject(activity: Activity)
    fun exposeDataManager(): DataManager
    fun exposeNetworkingInterface(): NetworkingInterface
}