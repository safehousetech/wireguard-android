package com.safehouse.core.api.Requester;

import android.content.Context;

import com.safehouse.core.api.ApiResponse;
import com.safehouse.core.api.NetworkClient;
import com.safehouse.core.api.RequestExecutor;
import com.safehouse.core.data.TokenManager;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class LogoutRequester implements RequestExecutor<ResponseBody> {
  private NetworkClient networkClient;
  private Context context;
  private String deviceId;

  public LogoutRequester(NetworkClient networkClient, Context context, String deviceId) {
    this.networkClient = networkClient;
    this.context = context;
    this.deviceId = deviceId;
  }

  @Override
  public ApiResponse<ResponseBody> executeRequest(Boolean isMoreTimeoutNeeded) {
    try {
      networkClient.setIsMoreTimeoutNeeded(isMoreTimeoutNeeded);
      //            networkClient.addInterceptor(NetworkingInterface.InterceptorType.SIGNED_APIS);
      Response<ResponseBody> response =
          networkClient.createService().logout(deviceId).execute();
      switch (response.code()) {
        case 401:
          return ApiResponse.error("Wrong username or password. Please try again.", null, 401);
        case 200:
          // TODO: logic to be shifted away from LogoutRequester
          TokenManager tokenManager = new TokenManager(context);
          tokenManager.clearAll();
          return ApiResponse.success(response.body(), 200);

        case 404:
          return ApiResponse.error("404 Not Found", "404 Not Found", response.code());
        default:
          JSONObject jObjError = new JSONObject(response.errorBody().string());
          String errorMsg =
              jObjError.getString("message");
          return ApiResponse.error(errorMsg, response.errorBody().toString(), response.code());
      }
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
    return ApiResponse.error("404 Not Found", null, 404);
  }
}
