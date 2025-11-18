package com.safehouse.core.api;

import androidx.annotation.NonNull;


import com.safehouse.core.data.PaymentData;
import com.safehouse.core.model.*;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Apis {
  @POST("/api/v1/users/mobile/login")
  Call<LoginResponseObj> loginV3(@Body @NonNull LoginRequestObj loginRequest);

  @GET("/api/v1/users/mobile/licenses/")
  Call<GetLicenseResponse> getLicense();

  @POST("/api/v1/users/mobile/licenses/{licenseHash}/select")
  Call<ResponseBody> selectLicense(@Path("licenseHash") String licenseHash);

  // updated api doesn't need ip from client
//  jio-sphere/v1/wireguard/region'
  @GET("jio-sphere/v1/wireguard/region")
  Call<List<GetRegionResponse>> getRegion();

  @POST("/api/v1/auth/logout/{deviceId}")
  Call<ResponseBody> logout(@Path("deviceId") String deviceId);

  @POST("/jio-sphere/v1/wireguard/peerConfig/")
  Call<VPNConnectResponse> connectVPN(@Body VPNConnectRequest vpnConnectRequest);

  @POST("/jio-sphere/v1/wireguard/freePeerConfig")
  Call<VPNConnectResponse> connectFreeVPN(@Body FreeVPNConnectRequest freeVPNConnectRequest);

  @POST("/api/v1/users/mobile/licenses/purchase")
  Call<UpdateTransactionResponse> updateTransaction(@Body UpdateTransactionRequest updateTransactionRequest);

  @GET("/api/wg/blackList/")
  Call<GetExcludeAppResponse> getExcludeApps();

  @POST("/api/nondb/v1/support/")
  Call<UserSupportResponse> sendSupport(
      @Header("Region") String region, @Body UserSupportRequest userSupportRequest);

  @POST("/api/wg/resetPassword/")
  Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

  @POST("/api/v1/applications/android/origin")
  Call<AppLocationResponse> getApplicationLocation(@Body AppLocationRequest appLocationRequest);

  @GET("/api/v1/applications")
  Call<List<GetExcludeAppResponse>> getAllowedApps();

  @PATCH("/api/v1/users/mobile")
  Call<UpdateUserResponse> updateUser(@Body UpdateUserRequest updateUserRequest);

  @GET("/jio-sphere/v1/auth/token/refresh")
  Call<RefreshTokenResponse> refreshToken();

  @POST("/api/v1/users/mobile/token")
  Call<ReissueTokenResponse> reIssueTokenV2(
      @Body ReIssueTokenRequestV2 reIssueTokenRequest); // new api

  @POST("/api/v1/users/mobile/profile/verify")
  Call<VerifyUserResponseV2> verifyUserV2(@Body VerifyUserRequestV2 verifyUserRequest);

  @POST("/api/v1/users/mobile/licenses/{licenseHash}/attach")
  Call<ResponseBody> attachLicense(@Path("licenseHash") String licenseHash);

  @POST("/jio-sphere/v1/license/validate")
  Call<ValidateResponse> validate(@Body ValidateRequest validateRequest);

  @POST("/api/nondb/v1/linkwatch/")
  Call<ValidateURLResponse> validateURL(@Body ValidateURLRequest validateURLRequest);

  @POST("/api/v1/auth/sendOtp")
  Call<ResponseBody> sendOtpForVerification(@Body SendOTPRequest sendOTPRequest);

  @POST("/api/v1/auth/validateOTP")
  Call<ResponseBody> validateOTP(@Body ValidateOTPRequest validateOTPRequest);

  @GET("/api/v1/users/insurance/getPolicy")
  Call<List<PolicyDetailsResponse>> getPolicyDetails();

  @GET("api/v1/device/ip")
  Call<GetMyIpResponse> getMyIp();

  @POST("/api/v1/activity-event-log")
  Call<ResponseBody> postUsageStats(@Body UsageStatsRequest usageStatsRequest);

  @POST("/api/v1/insurance/acknowledge")
  Call<ResponseBody> acknowledgeCIOnboarding();

  @POST("tracker/v1/device/register")
  Call<DeviceRegisterResponse> registerDevice(@Body DeviceRegisterRequest deviceRegisterRequest);

  @PATCH("tracker/v1/feature/{deviceId}")
  Call<ResponseBody> setTrackerFeatureState(@Path("deviceId") String deviceId, @Body SetTrackerFeatureRequest setTrackerFeatureRequest);

  @POST("tracker/v1/feature/location/{deviceId}")
  Call<ResponseBody> sendDeviceLocation(@Path("deviceId") String deviceId, @Body SendDeviceLocationRequest sendDeviceLocationRequest);

  @Multipart
  @POST("tracker/v1/feature/snapshot/{deviceId}")
  Call<ResponseBody> sendSnapshot(@Path("deviceId") String deviceId, @Part MultipartBody.Part snapshot);

  @POST("tracker/v1/pin/set/{deviceId}")
  Call<ResponseBody> callSetPin(@Path("deviceId") String deviceId, @Body SetChangePINRequest setChangePINRequest);

  @POST("tracker/v1/pin/validate/{deviceId}")
  Call<ResponseBody> callValidatePin(@Path("deviceId") String deviceId, @Body SetChangePINRequest setChangePINRequest);

  @GET("tracker/v1/pin/forgot/{deviceId}")
  Call<ResponseBody> callForgotPIN(@Path("deviceId") String deviceId);

//  @POST("sdk/v1/auth/mobile/login")
  @POST("jio-sphere/v1/auth/mobile/login")
  Call<LoginExternalResponse> callLoginExternal(@Body Login3rdPartyRequest login3RdPartyRequest);

//  jio-sphere/v1/wireguard/vpnStats'
  @POST("jio-sphere/v1/wireguard/vpnStats")
  Call<ResponseBody> uploadIpData(@Body UploadIpRequest uploadIpRequest);

  @POST("jio-sphere/v1/wireguard/removePeerConfig")
  Call<ResponseBody> removePeerConfig(@Body RemovePeerRequest removePeerRequest);

  @POST("jio-sphere/v1/order/create/payment-link")
  Call<PaymentResponse> getPaymentLink(@Body PaymentData paymentData);

  @POST("jio-sphere/v1/wireguard/removeFreePeerConfig")
  Call<ResponseBody> removeFreePeerConfig(@Body RemovePeerRequest removePeerRequest);

  @GET("jio-sphere/v1/license/getPlans")
  Call<MyPlanResponse> getPlans();
}
