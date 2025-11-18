package com.safehouse.core.api.Requester;

import com.safehouse.core.api.ApiResponse;
import com.safehouse.core.api.NetworkClient;
import com.safehouse.core.api.RequestExecutor;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class SelectLicenseRequester implements RequestExecutor<ResponseBody> {
  private NetworkClient networkClient;
  private String licenseHash;

  public SelectLicenseRequester(
      NetworkClient networkClient, String licenseHash) {
    this.networkClient = networkClient;
    this.licenseHash = licenseHash;
  }

  @Override
  public ApiResponse<ResponseBody> executeRequest(Boolean isMoreTimeoutNeeded) {
    try {
      networkClient.setIsMoreTimeoutNeeded(isMoreTimeoutNeeded);
      //            networkClient.addInterceptor(NetworkingInterface.InterceptorType.SIGNED_APIS);
      Response<ResponseBody> response =
          networkClient.createService().selectLicense(licenseHash).execute();
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
              jObjError.getString("message");
          return ApiResponse.error(errorMsg, response.errorBody().toString(), response.code());
      }
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
    return ApiResponse.error("404 Not Found", null, 404);
  }
}
