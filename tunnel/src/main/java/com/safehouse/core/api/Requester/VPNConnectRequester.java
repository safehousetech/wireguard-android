package com.safehouse.core.api.Requester;

import com.safehouse.core.api.ApiResponse;
import com.safehouse.core.api.NetworkClient;
import com.safehouse.core.api.RequestExecutor;
import com.safehouse.core.api.interceptors.UserIPInterceptor;
import com.safehouse.core.model.VPNConnectRequest;
import com.safehouse.core.model.VPNConnectResponse;
import com.safehouse.core.network.NetworkingInterface;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

@Singleton
public class VPNConnectRequester implements RequestExecutor<VPNConnectResponse> {
  private NetworkClient networkClient;
  private VPNConnectRequest vpnConnectRequest;
  private String ip;

  @Inject
  public VPNConnectRequester(NetworkClient networkClient) {
    this.networkClient = networkClient;
  }

  public VPNConnectRequester(NetworkClient networkClient, VPNConnectRequest vpnConnectRequest) {
    this.networkClient = networkClient;
    this.vpnConnectRequest = vpnConnectRequest;
  }

  public void setVpnConnectRequest(String ip, VPNConnectRequest vpnConnectRequest) {
    this.vpnConnectRequest = vpnConnectRequest;
    this.ip = ip;
  }

  @Override
  public ApiResponse<VPNConnectResponse> executeRequest(Boolean isMoreTimeoutNeeded) {
    try {
      networkClient.setIsMoreTimeoutNeeded(isMoreTimeoutNeeded);
      networkClient.addInterceptor(NetworkingInterface.InterceptorType.SIGNED_APIS_WITH_LOCATION);
      networkClient.addInterceptor(new UserIPInterceptor(ip));
      Response<VPNConnectResponse> response =
          networkClient.createService().connectVPN(vpnConnectRequest).execute();
      if(response.isSuccessful())
        return ApiResponse.success(response.body(), 200);
      switch (response.code()) {
        case 401:
          return ApiResponse.error("Wrong username or password. Please try again.", null, 401);
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
