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
public class ModifyPassInterceptor implements Interceptor {

  private final TokenManager tokenManager;

  @Inject
  public ModifyPassInterceptor(TokenManager tokenManager) {
    this.tokenManager = tokenManager;
  }

  @Override
  public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
//    Log.e("Interceptor:", "ModifyPassInterceptor : intercept : " + chain.request().toString());
    final String modifyPaswdToken = tokenManager.getModifyPaswdToken();
    Request original = chain.request();
    Request request =
        original
            .newBuilder()
            .addHeader(Headers.INSTANCE.getCONTENT_TYPE(), Headers.Values.INSTANCE.getAPPLICATION_JSON())
            .addHeader(Headers.INSTANCE.getACCEPT(), Headers.Values.INSTANCE.getAPPLICATION_JSON())
            .addHeader(Headers.INSTANCE.getACCEPT_LANGUAGE(), LanguageCodeHelper.INSTANCE.getLanguageCode())
                .addHeader(Headers.INSTANCE.getAPP_VERSION(), "module")
            .addHeader(Headers.INSTANCE.getAUTHORIZATION(), "Bearer " + modifyPaswdToken)
            .method(original.method(), original.body())
            .build();
    return chain.proceed(request);
  }
}
