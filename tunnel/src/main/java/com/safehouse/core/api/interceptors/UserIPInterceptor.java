package com.safehouse.core.api.interceptors;

import com.safehouse.core.api.Headers;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class UserIPInterceptor implements Interceptor {

  private final String ip;

  @Inject
  public UserIPInterceptor(String ip) {
    this.ip = ip;
  }

  @Override
  public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
    Request original = chain.request();
    Request request =
        original
            .newBuilder()
            .addHeader(Headers.INSTANCE.getUSER_IP(), ip)
            .method(original.method(), original.body())
            .build();
    return chain.proceed(request);
  }
}
