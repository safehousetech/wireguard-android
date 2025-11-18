package com.safehouse.core.api.Requester;

import com.safehouse.core.api.ApiResponse;
import com.safehouse.core.api.NetworkClient;
import com.safehouse.core.api.RequestExecutor;
import com.safehouse.core.model.AppLocationRequest;
import com.safehouse.core.model.AppLocationResponse;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

@Singleton
public class GetAppLocationRequester implements RequestExecutor<AppLocationResponse> {
  private NetworkClient networkClient;
  private AppLocationRequest appLocationRequest;

  @Inject
  public GetAppLocationRequester(NetworkClient networkClient) {
    this.networkClient = networkClient;
  }

  public GetAppLocationRequester(
      NetworkClient networkClient, AppLocationRequest appLocationRequest) {
    this.networkClient = networkClient;
    this.appLocationRequest = appLocationRequest;
  }

  public void setAppLocationRequest(AppLocationRequest appLocationRequest) {
    this.appLocationRequest = appLocationRequest;
  }

  @Override
  public ApiResponse<AppLocationResponse> executeRequest(Boolean isMoreTimeoutNeeded) {
    try {
      networkClient.setIsMoreTimeoutNeeded(isMoreTimeoutNeeded);
      //            networkClient.addInterceptor(NetworkingInterface.InterceptorType.SIGNED_APIS);
      Response<AppLocationResponse> response =
          networkClient.createService().getApplicationLocation(appLocationRequest).execute();
      switch (response.code()) {
        case 401:
          return ApiResponse.error("Wrong username or password. Please try again.", null, 401);
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
