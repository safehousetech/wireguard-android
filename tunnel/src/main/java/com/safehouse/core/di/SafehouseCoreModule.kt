package com.safehouse.core.di

import android.content.Context
import com.safehouse.core.api.NetworkClient
import com.safehouse.core.data.AppDataManager
import com.safehouse.core.data.Data
import com.safehouse.core.data.DataManager
import com.safehouse.core.data.TokenManager
import com.safehouse.core.network.NetworkDataManager
import com.safehouse.core.network.NetworkingInterface
import com.safehouse.core.util.Config
import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class SafehouseCoreModule {
    @Singleton
    @Provides
    fun getData(context: Context): Data = Data.getInstance(context)

    @Singleton
    @Provides
    fun getConfig(context: Context): Config = Config(context)

    @Singleton
    @Provides
    fun getTokenManager(context: Context): TokenManager = TokenManager(context)

    @Singleton
    @Provides
    fun provideDataManager(appDataManager: AppDataManager): DataManager = appDataManager

    @Singleton
    @Provides
    fun provideNetworkDataManager(networkDataManager: NetworkDataManager): NetworkingInterface =
    networkDataManager


    @Singleton
    @Provides
    @Named("Retrofit.lessTimeouts")
    fun provideRetrofitWithLessTimeouts(
        context: Context,
        @Named("OkHttpClient.lessTimeouts") client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder() // .baseUrl("https://api.safehousetech.com")
            //            .baseUrl(BuildConfig.BASE_URL)
            .baseUrl(NetworkClient.ENV_DEF)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named("Retrofit.moreTimeouts")
    fun provideRetrofitWithMoreTimeouts(
        context: Context,
        @Named("OkHttpClient.moreTimeouts") client: OkHttpClient
    ): Retrofit {
//        val provideBuildUtils = provideBuildUtils(context)
        return Retrofit.Builder() // .baseUrl("https://api.safehousetech.com")
            .baseUrl(NetworkClient.ENV_DEF)
            //            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named("Retrofit.masterApiCalls")
    fun provideRetrofitForMasterApiCalls(
        context: Context,
        @Named("OkHttpClient.masterApiCalls") okHttpClient: OkHttpClient
    ): com.safehouse.core.api.MasterApiInterface {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(NetworkClient.ENV_DEF)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        return retrofit.create(com.safehouse.core.api.MasterApiInterface::class.java)
    }
}