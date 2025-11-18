package com.safehouse.core.api.interceptors;


import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.safehouse.core.data.TokenManager;
import com.safehouse.core.util.LanguageCodeHelper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class SignedApisWithLocationInterceptor implements Interceptor {
  public static String location = "23.027298, 78.007217";
  private final TokenManager tokenManager;

  @Inject
  public SignedApisWithLocationInterceptor(TokenManager tokenManager) {
    this.tokenManager = tokenManager;
  }

  @Override
  public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
    Request original = chain.request();
    final String auth = tokenManager.getToken();
    Request request =
            original
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Accept-Language", LanguageCodeHelper.INSTANCE.getLanguageCode())
                    .addHeader("x-client-location", location)
                    .header("Authorization", "Bearer " + auth)
                    .method(original.method(), original.body())
                    .build();
    return chain.proceed(request);
  }

  private String getLatLong(){
    if(isCountryIndia(tokenManager.getContext()))
      return "23.027298, 78.007217";
    else
      return "50.651801, 10.071007";
  }

  public static Boolean isCountryIndia(Context context) {
    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    String countryCodeValue = "in";
    if (tm != null && !TextUtils.isEmpty(tm.getNetworkCountryIso())) {
      countryCodeValue = tm.getNetworkCountryIso();
    }

    return countryCodeValue.equalsIgnoreCase("in");
  }
}
