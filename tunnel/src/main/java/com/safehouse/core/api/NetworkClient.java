package com.safehouse.core.api;

import android.content.Context;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.safehouse.core.api.interceptors.ModifyPassInterceptor;
import com.safehouse.core.api.interceptors.ReIssueTokenInterceptor;
import com.safehouse.core.api.interceptors.RefreshTokenInterceptor;
import com.safehouse.core.api.interceptors.SignedApisInterceptor;
import com.safehouse.core.api.interceptors.SignedApisWithLocationInterceptor;
import com.safehouse.core.api.interceptors.UnSignedApisInterceptor;
import com.safehouse.core.api.interceptors.LoginExternalApiInterceptor;
import com.safehouse.core.data.TokenManager;
import com.safehouse.core.network.NetworkingInterface;

import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Created by Vishal Gaur on 3/22/2018. */
@Singleton
public class NetworkClient {
//  public static final String ENV_DEF = "https://api-dev-sensorise.safehousetech.com";
//  public static final String ENV_DEF = "https://jio-sphere-dev.safehousetech.com/";
  public static final String ENV_DEF = "https://api-jiosphere.safehousetech.com/";

  private static final boolean LOGGER_ENABLED = true;
  private Context mContext;
  private Boolean moreTimeoutNeeded = false;

  private Retrofit retrofitWithLessTimeouts;
  private Retrofit retrofitWithMoreTimeouts;

  private OkHttpClient client;
  private OkHttpClient.Builder httpClient;
  private boolean httpClientCreated = false;
  public TokenManager tokenManager;

  @Inject
  public NetworkClient(
      @Named("Retrofit.lessTimeouts") Retrofit retrofitWithLessTimeouts,
      @Named("Retrofit.moreTimeouts") Retrofit retrofitWithMoreTimeouts,
//      BuildUtils buildUtils,
      Context context) {
    this.retrofitWithLessTimeouts = retrofitWithLessTimeouts;
    this.retrofitWithMoreTimeouts = retrofitWithMoreTimeouts;
//    this.buildUtils = buildUtils;
    tokenManager = new TokenManager(context);
    if (tokenManager != null) {
    }
  }

  public NetworkClient(
      boolean isMoreTimeoutNeeded,
      Context mContext,
      NetworkingInterface.InterceptorType interceptorType) {
    if (tokenManager != null) {

    }
    this.mContext = mContext;
    tokenManager = new TokenManager(mContext);
//    buildUtils = new BuildUtils(mContext);
    moreTimeoutNeeded=isMoreTimeoutNeeded;

    createHttpClient();
//    Log.v("api/", "interceptorType=" + interceptorType);
    switch (interceptorType) {
      case MODIFY_PASS:
        httpClient.addInterceptor(new ModifyPassInterceptor(tokenManager));
        break;
      case REFRESH_TOKEN:
        httpClient.addInterceptor(new RefreshTokenInterceptor(tokenManager));
        break;
      case REISSUE_TOKEN:
        httpClient.addInterceptor(new ReIssueTokenInterceptor(tokenManager));
        break;
      case SIGNED_APIS:
        httpClient.addInterceptor(new SignedApisInterceptor(tokenManager));
        break;
      case UNSIGNED_APIS:
        httpClient.addInterceptor(new UnSignedApisInterceptor(tokenManager));
        break;
      case SIGNED_APIS_WITH_LOCATION:
        httpClient.addInterceptor(new SignedApisWithLocationInterceptor(tokenManager));
        break;
      case LOGIN_EXTERNAL:
        httpClient.addInterceptor(new LoginExternalApiInterceptor());
        break;
    }


//    if(BuildConfig.LOGGER_ENABLED) {
    if(LOGGER_ENABLED) {
      ChuckerInterceptor.Builder chuckerbuilder = new ChuckerInterceptor.Builder(mContext);
      httpClient.addInterceptor(chuckerbuilder.build());
    }

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

//    if (BuildConfig.DEBUG) {
    if(LOGGER_ENABLED) {
      logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    else logging.setLevel(HttpLoggingInterceptor.Level.NONE);
    httpClient.addInterceptor(logging);
    client = httpClient.build();
    defaultRetrofit(client);
  }

  private void createHttpClient() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

//    if (BuildConfig.DEBUG) {
    if(LOGGER_ENABLED) {
      logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    else logging.setLevel(HttpLoggingInterceptor.Level.NONE);

    httpClient = new OkHttpClient.Builder();

    if (!moreTimeoutNeeded) {
      httpClient.connectTimeout(6, TimeUnit.SECONDS);
      httpClient.readTimeout(8, TimeUnit.SECONDS);
      httpClient.writeTimeout(6, TimeUnit.SECONDS);
    } else {
      httpClient.connectTimeout(12, TimeUnit.SECONDS);
      httpClient.readTimeout(16, TimeUnit.SECONDS);
      httpClient.writeTimeout(12, TimeUnit.SECONDS);
    }

//    httpClient.addInterceptor(logging);
    httpClientCreated = true;
  }

  private void defaultRetrofit(OkHttpClient client) {
    retrofitWithLessTimeouts =
        new Retrofit.Builder()
            .baseUrl(ENV_DEF)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    retrofitWithMoreTimeouts =
        new Retrofit.Builder()
            .baseUrl(ENV_DEF)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
  }

  public void setIsMoreTimeoutNeeded(Boolean moreTimeoutNeeded) {
    this.moreTimeoutNeeded = moreTimeoutNeeded;
  }

  public Apis createService() {
    if (moreTimeoutNeeded) {
      return retrofitWithMoreTimeouts.create(Apis.class);
    } else {
      return retrofitWithLessTimeouts.create(Apis.class);
    }
  }

  public void addInterceptor(Interceptor interceptor) {
    if (!httpClientCreated) {
      createHttpClient();
    }
    httpClient.addInterceptor(interceptor);
  }

  public void addInterceptor(NetworkingInterface.InterceptorType interceptorType) {
    if (!httpClientCreated) {
      createHttpClient();
    }
//    Log.v("api/", "interceptorType=" + interceptorType);
    switch (interceptorType) {
      case MODIFY_PASS:
        httpClient.addInterceptor(new ModifyPassInterceptor(tokenManager));
        break;
      case REFRESH_TOKEN:
        httpClient.addInterceptor(new RefreshTokenInterceptor(tokenManager));
        break;
      case REISSUE_TOKEN:
        httpClient.addInterceptor(new ReIssueTokenInterceptor(tokenManager));
        break;
      case SIGNED_APIS:
        httpClient.addInterceptor(new SignedApisInterceptor(tokenManager));
        break;
      case UNSIGNED_APIS:
        httpClient.addInterceptor(new UnSignedApisInterceptor(tokenManager));
        break;
      case SIGNED_APIS_WITH_LOCATION:
        httpClient.addInterceptor(new SignedApisWithLocationInterceptor(tokenManager));
        break;
      case LOGIN_EXTERNAL:
        httpClient.addInterceptor(new LoginExternalApiInterceptor());
        break;
    }

//    if(BuildConfig.LOGGER_ENABLED) {
    if(LOGGER_ENABLED) {
      ChuckerInterceptor.Builder chuckerbuilder = new ChuckerInterceptor.Builder(tokenManager.getContext());
      httpClient.addInterceptor(chuckerbuilder.build());
    }

    client = httpClient.build();
    defaultRetrofit(client);
  }
}
