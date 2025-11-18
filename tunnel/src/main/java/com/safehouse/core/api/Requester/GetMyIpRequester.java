package com.safehouse.core.api.Requester;

import com.safehouse.core.api.ApiResponse;
import com.safehouse.core.api.NetworkClient;
import com.safehouse.core.api.RequestExecutor;
import com.safehouse.core.model.GetMyIpResponse;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

@Singleton
public class GetMyIpRequester implements RequestExecutor<GetMyIpResponse> {
  private final NetworkClient networkClient;
  private String vpnEventType = "";

  @Inject
  public GetMyIpRequester(NetworkClient networkClient) {
    this.networkClient = networkClient;
  }

  @Override
  public ApiResponse<GetMyIpResponse> executeRequest(Boolean isMoreTimeoutNeeded) {
    try {
      networkClient.setIsMoreTimeoutNeeded(isMoreTimeoutNeeded);
      Response<GetMyIpResponse> response =
          networkClient.createService().getMyIp().execute();
      switch (response.code()) {
        case 200:
          return ApiResponse.success(response.body(), 200);
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

    return ApiResponse.error("404 Not Found", null, 404);
  }
}
