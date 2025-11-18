package com.safehouse.core.api.Requester;

import com.safehouse.core.api.ApiResponse;
import com.safehouse.core.api.NetworkClient;
import com.safehouse.core.api.RequestExecutor;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Response;

@Singleton
public class AttachLicenseRequester implements RequestExecutor<ResponseBody> {

  private final NetworkClient networkClient;
  private String licenseHash;

  @Inject
  public AttachLicenseRequester(NetworkClient networkClient) {
    this.networkClient = networkClient;
  }

  public AttachLicenseRequester(
      NetworkClient networkClient, String licenseHash) {
    this.networkClient = networkClient;
    this.licenseHash = licenseHash;
  }

  public void setLicenseHash(String licenseHash) {
    this.licenseHash = licenseHash;
  }

  @Override
  public ApiResponse<ResponseBody> executeRequest(Boolean isMoreTimeNeeded) {
    try {
      networkClient.setIsMoreTimeoutNeeded(isMoreTimeNeeded);
//      networkClient.addInterceptor(NetworkingInterface.InterceptorType.SIGNED_APIS);
      Response<ResponseBody> response =
          networkClient.createService().attachLicense(licenseHash).execute();
      if(response.isSuccessful())
        return ApiResponse.success(response.body(), 200);
      switch (response.code()) {
        case 401:
          return ApiResponse.error("Something went wrong. Couldn't get token from api", null, 401);
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
    return null;
  }
}
