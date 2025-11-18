package com.safehouse.core.api.interceptors;

import com.safehouse.core.api.Headers;
import com.safehouse.core.data.TokenManager;
import com.safehouse.core.util.LanguageCodeHelper;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import org.jetbrains.annotations.NotNull;

@Singleton
public class RefreshTokenInterceptor implements Interceptor {

  private final TokenManager tokenManager;

  @Inject
  public RefreshTokenInterceptor(TokenManager tokenManager) {
    this.tokenManager = tokenManager;
  }

  @Override
  public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
//    Log.e("Interceptor:", "RefreshTokenInterceptor : intercept : " + chain.request().toString());
    final String refreshToken = tokenManager.getRefreshToken();
    Request original = chain.request();
    //        Timber.e("Refresh Token: %s", refreshToken);
    Request request =
        original
            .newBuilder()
            .addHeader(Headers.INSTANCE.getCONTENT_TYPE(), Headers.Values.INSTANCE.getAPPLICATION_JSON())
            .addHeader(Headers.INSTANCE.getACCEPT(), Headers.Values.INSTANCE.getAPPLICATION_JSON())
            .addHeader(Headers.INSTANCE.getACCEPT_LANGUAGE(), LanguageCodeHelper.INSTANCE.getLanguageCode())
                .addHeader(Headers.INSTANCE.getAPP_VERSION(), "module")
            .addHeader(Headers.INSTANCE.getREFRESH_TOKEN(), "Bearer " + refreshToken)
            .method(original.method(), original.body())
            .build();

    return chain.proceed(request);
  }
}
