package com.safehouse.core.api;

import com.safehouse.core.model.*;
import com.safehouse.core.model.apimodels.AuthenticateModel;
import com.safehouse.core.model.apimodels.DeviceAuthenticateResponseModel;
import com.safehouse.core.model.apimodels.LogoutDeviceResponse;
import com.safehouse.core.model.apimodels.RegisterAppModelResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MasterApiInterface {

  String localURL = "";

  // headers are now dynamically passed to the API's
  @FormUrlEncoded
  @POST("/apiv1/login/")
  Call<LoginResponse> postLoginInfo(
      @Field("user") String user,
      @Field("pass") String password,
      @Field("device") String device,
      @Field("app") String app);

  @FormUrlEncoded
  @POST("/apiv1/register_app/")
  Call<RegisterAppModelResponse> registerApp(
      @Field("user") String user,
      @Field("pass") String password,
      @Field("device") String device,
      @Field("app") String app,
      @Field("license") String license,
      @Field("client_os") String clientOS,
      @Field("id") String id,
      @Field("push_id") String push);

  /* Antitheft URL */
  // SB

  @POST(localURL + "/api/Device/authenticate1")
  Call<ResponseBaseModelClass<DeviceAuthenticateResponseModel>> deviceAuthenticate(
      @Header("Content-Type") String contentTypeHeader, @Body AuthenticateModel authenticateModel);

  @POST(localURL + "/api/Device/{deviceId}/forgetpin/{notificationType}")
  // @GET(localURL + "/api/Device/{deviceId}/ForgetSecurityPin/{notificationType}")
  Call<ResponseBaseModelClass<Boolean>> forgotSecurityPIN(
      @Header("Content-Type") String contentTypeHeader,
      @Path("deviceId") int deviceID,
      @Path("notificationType") String type);

  //   Data Post - lostPhoneApi,captureSnapshotApi
  @POST(localURL + "/api/LostDevice")
  Call<ResponseBaseModelClass<Long>> postAntiTheft(
      @Header("Content-Type") String contentTypeHeader,
      @Header("Authorization") String authHeader,
      @Body LostDeviceDTO lostDeviceDTO);

  //  Data Post - lostPhoneApi,captureSnapshotApi
  @POST(localURL + "/api/Device/{id}/Status/{status}")
  Call<ResponseBaseModelClass<LogoutDeviceResponse>> deviceLogout(
      @Header("Content-Type") String contentTypeHeader,
      @Header("Authorization") String authHeader,
      @Path("id") int deviceID,
      @Path("status") int status);
}
