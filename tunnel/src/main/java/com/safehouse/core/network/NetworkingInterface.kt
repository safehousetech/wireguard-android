package com.safehouse.core.network

import android.content.Context
import com.safehouse.core.api.ApiResponse
import com.safehouse.core.api.BreachApiResponseV2
import com.safehouse.core.data.Data
import com.safehouse.core.model.AppLocationResponse
import com.safehouse.core.model.GetExcludeAppResponse
import com.safehouse.core.model.GetLicenseResponse
import com.safehouse.core.model.GetMyIpResponse
import com.safehouse.core.model.GetRegionResponse
import com.safehouse.core.model.InstalledApp
import com.safehouse.core.model.LoginResponseObj
import com.safehouse.core.model.LostDeviceDTO
import com.safehouse.core.model.RefreshTokenResponse
import com.safehouse.core.model.ReissueTokenResponse
import com.safehouse.core.model.ResponseBaseModelClass
import com.safehouse.core.model.UpdateTransactionRequest
import com.safehouse.core.model.UpdateTransactionResponse
import com.safehouse.core.model.VPNConnectResponse
import com.safehouse.core.model.ValidateResponse
import com.safehouse.core.model.apimodels.AuthenticateModel
import com.safehouse.core.model.apimodels.DeviceAuthenticateResponseModel
import com.safehouse.core.model.apimodels.LogoutDeviceResponse
import okhttp3.ResponseBody

interface NetworkingInterface {
  fun callReissueTokenApiV2(
    isMoreTimeoutNeeded: Boolean?,
    email: String?,
    phone: String?,
    alternateEmail: String?,
    alternatePhone: String?,
    appName: String?,
    customerId: String?,
    deepLinkSource: String?,
    licenses: ArrayList<String?>?,
    id: String?,
    firstName: String?,
    otp: String?
  ): ApiResponse<ReissueTokenResponse?>?

  fun authenticateAndCallGetLicenseApi(
    isMoreTimeoutNeeded: Boolean
  ): ApiResponse<GetLicenseResponse>
  fun authenticateAndCallGetRegionsApi(): ApiResponse<List<GetRegionResponse>>
  fun callValidateUserApi(isMoreTimeoutNeeded: Boolean): ApiResponse<ValidateResponse>
  fun callLoginApiV2(
    user: String?,
    isMoreTimeoutNeeded: Boolean?,
    isPhoneNumberLogin: Boolean?
  ): ApiResponse<LoginResponseObj>? // new api

  fun callConnectVpnApi(ip : String, publicKey: String?, region: String?, mode: String): ApiResponse<VPNConnectResponse?>?
  fun logout(baseServerUrl: String?, appCode: String?): String?

  fun getAllowedApps(isMoreTimeoutNeeded: Boolean?): ApiResponse<List<GetExcludeAppResponse>>
  fun callAppLocationApi(packagesArray: List<String>): ApiResponse<AppLocationResponse>?
  fun getRefreshTokenApi(): ApiResponse<RefreshTokenResponse?>?
  fun callAttachLicenseApi(licenseHash: String): ApiResponse<ResponseBody>?

  // Master Api Calls

  fun postAntiTheftInfo(
    authHeader: String,
    lostDeviceDTO: LostDeviceDTO
  ): ResponseBaseModelClass<Long>
  fun logoutFromDevice(
    authHeader: String,
    deviceId: Int,
    status: Int
  ): ResponseBaseModelClass<LogoutDeviceResponse>

  fun deviceAuth(
    authenticateModel: AuthenticateModel
  ): ResponseBaseModelClass<DeviceAuthenticateResponseModel>

  // Apis- Moved from presenter/ui to this interface
  fun AuthenticateAndGetLicense(mData: Data?): ApiResponse<GetLicenseResponse?>?

  // ApiUtility
  fun logoutUser(mData: Data?, getApplicationContext: Context?): ApiResponse<ResponseBody>?

  fun licenseSelection(
    mData: Data,
    licenseHash: String,
    getApplicationContext: Context
  ): ApiResponse<ResponseBody>

  fun updateLicence(
    licenseLength: String?,
    licenseType: String?,
    androidId: String?,
    mData: Data,
    mReciept: UpdateTransactionRequest.Receipt?,
    isUpgrade: Boolean
  ): ApiResponse<UpdateTransactionResponse?>?

  fun validateUser(data: Data): ApiResponse<ValidateResponse?>?

  fun licenseSelection(mData: Data, licenseHash: String): ApiResponse<ResponseBody>

  fun getRefreshTokenApiForUserInstalledApps(mData: Data): ApiResponse<RefreshTokenResponse?>?

  fun getExcludeAppsRequest(mData: Data, mPackages: MutableList<InstalledApp>?): ApiResponse<AppLocationResponse>?

  fun getIpAddressFromServer(): ApiResponse<GetMyIpResponse>

  // Networking (Isolated from DataManager)
  companion object SignedApis

  /*InterceptorType - Decides which header to attach to API's*/
  enum class InterceptorType {
    MODIFY_PASS, // Used to pass modify pass token in the API as header
    REFRESH_TOKEN, // Used to pass refresh token in the API as header
    REISSUE_TOKEN, // Used to pass reissue token in the API as header
    SIGNED_APIS, // Used to pass auth token in the API as header
    UNSIGNED_APIS, // doesn't pass anything in the API as header
    SIGNED_APIS_WITH_LOCATION,
    LOGIN_EXTERNAL//Used to pass auth token in the API as header + location data
    ;

    fun toString(interceptorType: InterceptorType): String {
      return when (interceptorType) {
        MODIFY_PASS -> "Modify Password"
        REFRESH_TOKEN -> "Refresh Token"
        REISSUE_TOKEN -> "ReIssue Token"
        SIGNED_APIS -> "Auth Token"
        UNSIGNED_APIS -> "Unsigned Api"
        SIGNED_APIS_WITH_LOCATION -> "SIGNED_APIS_WITH_LOCATION"
        LOGIN_EXTERNAL ->  "LOGIN_EXERNAL"
      }
    }

    fun toInterceptorType(interceptorType: String): InterceptorType {
      return when (interceptorType) {
        "Modify Password" -> MODIFY_PASS
        "Refresh Token" -> REFRESH_TOKEN
        "ReIssue Token" -> REISSUE_TOKEN
        "Auth Token" -> SIGNED_APIS
        "Unsigned Api" -> UNSIGNED_APIS
        "LOGIN_EXTERNAL" ->  LOGIN_EXTERNAL
        else -> UNSIGNED_APIS
      }
    }
  }
}
