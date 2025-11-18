package com.safehouse.core.api.Requester;

import com.safehouse.core.api.ApiResponse;
import com.safehouse.core.api.NetworkClient;
import com.safehouse.core.api.RequestExecutor;
import com.safehouse.core.model.GetLicenseResponse;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

@Singleton
public class GetLicenseRequester implements RequestExecutor<GetLicenseResponse> {

  private final NetworkClient networkClient;

  @Inject
  public GetLicenseRequester(NetworkClient networkClient) {
    this.networkClient = networkClient;
  }

  @Override
  public ApiResponse<GetLicenseResponse> executeRequest(Boolean isMoreTimeoutNeeded) {
    try {
      networkClient.setIsMoreTimeoutNeeded(isMoreTimeoutNeeded);
      //            networkClient.addInterceptor(NetworkingInterface.InterceptorType.SIGNED_APIS);
      Response<GetLicenseResponse> response = networkClient.createService().getLicense().execute();
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
