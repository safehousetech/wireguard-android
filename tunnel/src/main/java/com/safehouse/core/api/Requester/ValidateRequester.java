package com.safehouse.core.api.Requester;

import com.safehouse.core.api.ApiResponse;
import com.safehouse.core.api.NetworkClient;
import com.safehouse.core.api.RequestExecutor;
import com.safehouse.core.model.ValidateRequest;
import com.safehouse.core.model.ValidateResponse;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

@Singleton
public class ValidateRequester implements RequestExecutor<ValidateResponse> {
  private final NetworkClient networkClient;
  private final ValidateRequest validateRequest;

  @Inject
  public ValidateRequester(NetworkClient networkClient, ValidateRequest validateRequest) {
    this.networkClient = networkClient;
    this.validateRequest = validateRequest;
  }

  @Override
  public ApiResponse<ValidateResponse> executeRequest(Boolean isMoreTimeNeeded) {
    try {
      networkClient.setIsMoreTimeoutNeeded(isMoreTimeNeeded);
      // networkClient.addInterceptor(NetworkingInterface.InterceptorType.SIGNED_APIS);
      Response<ValidateResponse> response =
          networkClient.createService().validate(validateRequest).execute();
      switch (response.code()) {
        case 400:
          return ApiResponse.error(
              "Current License expired",
              ValidateResponse.fromString(response.errorBody().string()),
              400);
        case 401:
          return ApiResponse.error("Wrong username or password. Please try again.", null, 401);
        case 200:
          return ApiResponse.success(response.body(), 200);
        case 404:
          return ApiResponse.error("404 Not Found", "404 Not Found", 404);
        case 422:
          return ApiResponse.error(
              "422 - No License or Expired License",
              ValidateResponse.fromString(response.errorBody().string()),
              422);
        default:
          JSONObject jObjError = new JSONObject(response.errorBody().string());
          String errorMsg =
              jObjError.getJSONObject("error").getJSONObject("result").getString("message");
          return ApiResponse.error(errorMsg, response.errorBody().toString(), response.code());
      }
    } catch (IOException | JSONException e) {
      e.printStackTrace();
      return ApiResponse.error("404 Not Found", null, 404);
    }
  }
}
