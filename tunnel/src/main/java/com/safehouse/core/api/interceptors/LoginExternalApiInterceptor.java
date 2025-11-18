package com.safehouse.core.api.interceptors;

import com.safehouse.core.api.Headers;
import com.safehouse.core.util.LanguageCodeHelper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class LoginExternalApiInterceptor implements Interceptor {

  @Inject
  public LoginExternalApiInterceptor() {

  }

  @Override
  public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
    Request original = chain.request();
    Request request =
        original
                .newBuilder()
                .addHeader(Headers.INSTANCE.getCONTENT_TYPE(), Headers.Values.INSTANCE.getAPPLICATION_JSON())
                .addHeader(Headers.INSTANCE.getACCEPT(), Headers.Values.INSTANCE.getAPPLICATION_JSON())
                .addHeader(Headers.INSTANCE.getACCEPT_LANGUAGE(), LanguageCodeHelper.INSTANCE.getLanguageCode())
                .addHeader(Headers.INSTANCE.getAPP_VERSION(), "module")
                .addHeader(Headers.INSTANCE.getAUTHORIZATION(), "Basic amlvLXNwaGVyZTp6OEc0SnBYMXFXOUxyVDVWbksyTTZZQmQzQzBG")
                .method(original.method(), original.body())
                .build();
    return chain.proceed(request);
  }
}
