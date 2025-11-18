package com.safehouse.core.api.Requester;

import com.safehouse.core.api.ApiResponse;
import com.safehouse.core.api.NetworkClient;
import com.safehouse.core.api.RequestExecutor;
import com.safehouse.core.model.LoginRequestObj;
import com.safehouse.core.model.LoginResponseObj;
import com.safehouse.core.network.NetworkingInterface;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

@Singleton
public class LoginRequesterV2 implements RequestExecutor<LoginResponseObj> {

  private final NetworkClient networkClient;
  private LoginRequestObj loginRequest;

  @Inject
  public LoginRequesterV2(NetworkClient networkClient) {
    this.networkClient = networkClient;
  }

  public LoginRequesterV2(NetworkClient networkClient, LoginRequestObj loginRequest) {
    this.networkClient = networkClient;
    this.loginRequest = loginRequest;
  }

  public void setLoginRequest(LoginRequestObj loginRequest) {
    this.loginRequest = loginRequest;
  }

  @Override
  public ApiResponse<LoginResponseObj> executeRequest(Boolean isMoreTimeNeeded) {
    try {
      networkClient.setIsMoreTimeoutNeeded(isMoreTimeNeeded);
      networkClient.addInterceptor(NetworkingInterface.InterceptorType.UNSIGNED_APIS);
      Response<LoginResponseObj> response =
          networkClient.createService().loginV3(loginRequest).execute();
      if(response.isSuccessful())
        return ApiResponse.success(response.body(), 200);
      switch (response.code()) {
        case 401:
          return ApiResponse.error("Wrong username or phone. Please try again.", null, 401);
        case 404:
          return ApiResponse.error("404 Not Found", "404 Not Found", response.code());
        default:
          JSONObject jObjError = new JSONObject(response.errorBody().string());
          String errorMsg = jObjError.getJSONArray("message").getString(0);
          return ApiResponse.error(errorMsg, response.errorBody().toString(), response.code());
      }
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
