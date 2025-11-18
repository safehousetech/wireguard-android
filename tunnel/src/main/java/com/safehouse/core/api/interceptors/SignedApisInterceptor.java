package com.safehouse.core.api.interceptors;

import com.safehouse.core.data.TokenManager;
import com.safehouse.core.api.Headers;
import com.safehouse.core.util.LanguageCodeHelper;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

@Singleton
public class SignedApisInterceptor implements Interceptor {

  private final TokenManager tokenManager;

  @Inject
  public SignedApisInterceptor(TokenManager tokenManager) {
    this.tokenManager = tokenManager;
  }

  @Override
  public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
    Request original = chain.request();
    final String auth = tokenManager.getToken();
    Request request =
        original
            .newBuilder()
            .addHeader(Headers.INSTANCE.getCONTENT_TYPE(), Headers.Values.INSTANCE.getAPPLICATION_JSON())
            .addHeader(Headers.INSTANCE.getACCEPT(), Headers.Values.INSTANCE.getAPPLICATION_JSON())
            .addHeader(Headers.INSTANCE.getACCEPT_LANGUAGE(), LanguageCodeHelper.INSTANCE.getLanguageCode())
                .addHeader(Headers.INSTANCE.getAPP_VERSION(), "module")
            .addHeader(Headers.INSTANCE.getAUTHORIZATION(),"Bearer " + auth)
            .method(original.method(), original.body())
            .build();
    return chain.proceed(request);
  }
}
