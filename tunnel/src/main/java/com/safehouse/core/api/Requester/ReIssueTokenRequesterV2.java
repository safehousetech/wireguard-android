package com.safehouse.core.api.Requester;

import com.safehouse.core.api.ApiResponse;
import com.safehouse.core.api.NetworkClient;
import com.safehouse.core.api.RequestExecutor;
import com.safehouse.core.model.ReIssueTokenRequestV2;
import com.safehouse.core.model.ReissueTokenResponse;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

@Singleton
public class ReIssueTokenRequesterV2 implements RequestExecutor<ReissueTokenResponse> {

  private final NetworkClient networkClient;
  private ReIssueTokenRequestV2 reIssueTokenRequest;

  @Inject
  public ReIssueTokenRequesterV2(NetworkClient networkClient) {
    this.networkClient = networkClient;
  }

  public ReIssueTokenRequesterV2(
      NetworkClient networkClient, ReIssueTokenRequestV2 reIssueTokenRequest) {
    this.networkClient = networkClient;
    this.reIssueTokenRequest = reIssueTokenRequest;
  }

  @Override
  public ApiResponse<ReissueTokenResponse> executeRequest(Boolean isMoreTimeNeeded) {
    try {
      networkClient.setIsMoreTimeoutNeeded(isMoreTimeNeeded);
      //            networkClient.addInterceptor(NetworkingInterface.InterceptorType.REISSUE_TOKEN);
      Response<ReissueTokenResponse> response =
          networkClient.createService().reIssueTokenV2(reIssueTokenRequest).execute();
      switch (response.code()) {
        case 401:
          return ApiResponse.error("Something went wrong. Couldn't get token from api", null, 401);
        case 200:
          return ApiResponse.success(response.body(), 200);
        case 201:
          return ApiResponse.success(response.body(), 201);
        case 404:
          return ApiResponse.error("404 Not Found", "404 Not Found", response.code());
        default:
          JSONObject jObjError = new JSONObject(response.errorBody().string());
          String errorMsg =
              jObjError.getJSONObject("error").getJSONObject("result").getString("message");
          return ApiResponse.error(errorMsg, response.errorBody().toString(), response.code());
      }
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
