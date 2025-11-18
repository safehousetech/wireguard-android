package com.safehouse.core.data

import android.annotation.SuppressLint
import android.content.Context
import javax.inject.Inject

@SuppressLint("LogNotTimber")
class TokenManager @Inject constructor(appContext: Context) :
  PersistentPreferences(appContext, "TokenPrefs") {
  var token: String
    set(value) {
      return setString("token", value)
    }
    get() {
      return getString("token", "")
    }
  var refreshToken: String
    set(value) {
      setString("refreshToken", value)
    }
    get() {
      return getString("refreshToken", "")
    }

  var dynamicLinkToken: String
    set(value) {
      setString("dynamicLinkToken", value)
    }
    get() {
      return getString("dynamicLinkToken", "")
    }

  var verifyUserOtp: String
    set(value) {
      setString("verifyUserOtp", value)
    }
    get() {
      return getString("verifyUserOtp", "")
    }

  var modifyPaswdToken: String
    set(value) {
      setString("modifyPaswdToken", value)
    }
    get() {
      return getString("modifyPaswdToken", "")
    }

  var pushIDToken: String
    set(value) {
      setString("pushIDToken", value)
    }
    get() {
      return getString("pushIDToken", "")
    }

  // TODO we should remove mixpanel token in next iteration.
  var mixpanelToken: String
    set(value) {
      setString("mixpanelToken", value)
    }
    get() {
      return getString("mixpanelToken", "")
    }

  var fcmToken: String
    set(value) {
      setString("fcmToken", value)
    }
    get() {
      var token = getString("fcmToken", "")
      if (token.isBlank()) {
        // token = Tasks.await(FirebaseMessaging.getInstance().token)
        setString("fcmToken", token)
        mixpanelToken = token
      }
      return token
    }

  var deviceAuthToken: String
    set(value) {
      setString(DEVICE_AUTH_TOKEN, value)
    }
    get() {
      return getString(DEVICE_AUTH_TOKEN, "")
    }
  var deviceAuthRefreshToken: String
    set(value) {
      setString(DEVICE_AUTH_REFRESHTOKEN, value)
    }
    get() {
      return getString(DEVICE_AUTH_REFRESHTOKEN, "")
    }
  var deviceAuthTokenExpiry: String
    set(value) {
      setString(DEVICE_AUTH_TOKEN_EXPIRY, value)
    }
    get() {
      return getString(DEVICE_AUTH_TOKEN_EXPIRY, "")
    }

  val DEVICE_AUTH_TOKEN = "deviceAuthToken"
  val DEVICE_AUTH_REFRESHTOKEN = "deviceAuthRefreshToken"
  val DEVICE_AUTH_TOKEN_EXPIRY = "deviceAuthTokenExpiry"

  fun clearAll() {
    token = ""
    refreshToken = ""
    dynamicLinkToken = ""
    verifyUserOtp = ""
    modifyPaswdToken = ""
    pushIDToken = ""
    mixpanelToken = ""
    deviceAuthToken = ""
    deviceAuthRefreshToken = ""
    deviceAuthTokenExpiry = ""
  }
}
