package com.safehouse.core.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Data {
  private static volatile Data instance;
  private static Object mutex = new Object();

  Context context;

  public static final String PREFS_NAME = "MyPrefsFile";

  public static String INTRUDER_ACTIVATE_STATUS = "intruder_activate_status";
  public static String IN_CORRECT_PIN_COUNT = "incorrect_pin_count";
  public static String DEVICE_SERVER_ID = "device_server_id";
  public static String LOCATION_FETCH_COUNT = "location_fetch_count";
  public static String LOCATION_ALARM_ALREADY_RUN = "location_alarm_already_run";

  public final String LOGIN_MODEL = "loginModel";
  public final String APP_LOCK_PIN = "appLockPin";
  public final String userID = "userID";
  public final String ANDROID_ID = "androidId";

  public final String ANTI_THEFT_LOCATION_STATUS = "antiTheftLocationStatus";
  public final String ANTI_THEFT_LOCK_DEVICE_STATUS = "antiTheftLockDeviceStatus";
  public final String ANTI_THEFT_PLAY_TONE_STATUS = "antiTheftPlayToneStatus";
  public final String ANTI_THEFT_SNAP_SHOT_STATUS = "antiTheftSnapShotStatus";
  public final String ANTI_THEFT_SNAP_SHOT_ACTIVE = "antiTheftSnapShotActive";
  public final String ANTI_THEFT_ACTIVATE_STATUS = "antiTheftActivateStatus";

  public final String ANTI_THEFT_PLAYING_RINGTONE_STATUS = "antiTheftPlayingRingtoneStatus";

  public final String IS_LOGOUT = "isLogout";

  private String username;
  private String password;
  private String currentIP;
  private String region;
  private String firstName;
  private String lastName;
  private boolean isConnectedForReboot;
  private boolean isFirstTime;
  private boolean seenCCPitch;

  private boolean showVerifyCredsDialog;
  private String selectedRegion;
  private String customerID;

  private String publicKey;
  private String privateKey;
  private boolean isInstallRefererCalled;
  private String uniqueID;
  private boolean isEmailVerified;
  private boolean isPhoneVerified;
  private Boolean isAltcontactAdded;
  private Boolean isAltContactVerified;

  private Boolean isInsured;
  private Boolean shouldShowPaymentUiInProfile;
  private Boolean shouldShowPaymentUiNoLicenseInProfile;
  private Boolean showInsuranceValidation;

  private int showOnboardingCount;
  private String salutation;
  private String languageCode;
  private String deviceCurrentLanguageCode;
  private boolean isAdminPermissionHaveAsked;
  private boolean isLocationPermissionHaveAsked;
  private boolean isBackGroundLocationHaveAsked;
  private boolean isCameraPermissionHaveAsked;
  private String fastestServerCountryName;
  private boolean isStoragePermissionHaveAsked;

  private String serverConfig;
  private String adServerConfig;
  private long publicKeyGenerationTime;
  private String publicIP;

  private String selectedLicenseStartDate;
  private String selectedLicenseExpiryDate;
  private String selectedRegionName;
  private Set<String> selectedApps;
  private String jsonLicense;
  private String selectedLicense;
  private String licenseHash;
  private String appList;
  private String productType;
  private boolean includeAppsListUpdated;
  private String userPreferenceSelectedAppList;
  private String email;
  private String alternateEmail;
  private int userId;
  private String phoneNumber;
  private String alternatePhoneNumber;
  private String serverIp;
  private int versionCode;
  private String bestRegionCode;
  private String appLockStatus;

  // free trial
  private boolean isUserEligibleForFreeTrial;

  // free features
  private boolean areFeaturesFree;

  // vpn events
  private String currentVpnConnectionWireGuardServerLink;
  private String currentVpnConnectionPublicKey;

  // CI flow OTP verification
  private long lastRequestedOTPAt;

  // user metadata for events
  private String userCountryForEvents;

  // safety score
  private boolean seenSSOnboarding;
  private int safetyScore;
  private String safetyScoreScanResultJson;

  public boolean isInstallRefererCalled() {
    return isInstallRefererCalled;
  }

  public void setInstallRefererCalled(boolean installRefererCalled) {
    isInstallRefererCalled = installRefererCalled;
  }

  public String getUniqueID() {
    return uniqueID;
  }

  public void setUniqueID(String uniqueID) {
    this.uniqueID = uniqueID;
  }

  public boolean isEmailVerified() {
    return isEmailVerified;
  }

  public void setEmailVerified(boolean emailVerified) {
    isEmailVerified = emailVerified;
  }

  public boolean isPhoneVerified() {
    return isPhoneVerified;
  }

  public void setPhoneVerified(boolean phoneVerified) {
    isPhoneVerified = phoneVerified;
  }

  public boolean isInsured() {
    return isInsured;
  }

  public void setIsInsured(boolean isInsured) {
    this.isInsured = isInsured;
  }

  public boolean shouldShowPaymentUIInProfileScreen() {
    return shouldShowPaymentUiInProfile;
  }

  public void setShouldShowPaymentUIInProfileScreen(boolean shouldShowPaymentUiInProfile) {
    this.shouldShowPaymentUiInProfile = shouldShowPaymentUiInProfile;
  }

  public boolean shouldShowPaymentUINoLicenseInProfileScreen() {
    return shouldShowPaymentUiNoLicenseInProfile;
  }

  public void setShouldShowPaymentUINoLicenseInProfileScreen(
      boolean shouldShowPaymentUiNoLicenseInProfile) {
    this.shouldShowPaymentUiNoLicenseInProfile = shouldShowPaymentUiNoLicenseInProfile;
  }

  public Boolean getShowInsuranceValidation() {
    return showInsuranceValidation;
  }

  public void setShowInsuranceValidation(Boolean insurance) {
    this.showInsuranceValidation = insurance;
  }

  public String getSalutation() {
    return salutation;
  }

  public void setSalutation(String salutation) {
    this.salutation = salutation;
  }

  public String getLanguageCode() {
    //        return languageCode;
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//    Log.e("getLanguageCode", "languageCode " + settings.getString("languageCode", ""));
    return settings.getString("languageCode", "");
  }

  public void setLanguageCode(String languageCode) {
//    Log.e("setLanguageCode", "languageCode " + languageCode);
    this.languageCode = languageCode;
    // should save asap else, it gets overwrite and creates issue
    // save();
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString("languageCode", languageCode);
    editor.commit();
  }

  public String getDeviceCurrentLanguageCode() {
    return languageCode;
  }

  public void setDeviceCurrentLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  public boolean isAdminPermissionHaveAsked() {
    return isAdminPermissionHaveAsked;
  }

  public void setAdminPermissionHaveAsked(boolean adminPermissionHaveAsked) {
    isAdminPermissionHaveAsked = adminPermissionHaveAsked;
  }

  public boolean isLocationPermissionHaveAsked() {
    return isLocationPermissionHaveAsked;
  }

  public void setLocationPermissionHaveAsked(boolean locationPermissionHaveAsked) {
    isLocationPermissionHaveAsked = locationPermissionHaveAsked;
  }

  public boolean isBackGroundLocationHaveAsked() {
    return isBackGroundLocationHaveAsked;
  }

  public void setBackGroundLocationHaveAsked(boolean backGroundLocationHaveAsked) {
    isBackGroundLocationHaveAsked = backGroundLocationHaveAsked;
  }

  public String getFastestServerCountryName() {
    return fastestServerCountryName;
  }

  public void setFastestServerCountryName(String fastestServerCountryName) {
    this.fastestServerCountryName = fastestServerCountryName;
  }

  public boolean isCameraPermissionHaveAsked() {
    return isCameraPermissionHaveAsked;
  }

  public void setCameraPermissionHaveAsked(boolean cameraPermissionHaveAsked) {
    isCameraPermissionHaveAsked = cameraPermissionHaveAsked;
  }

  public boolean isStoragePermissionHaveAsked() {
    return isStoragePermissionHaveAsked;
  }

  public void setStoragePermissionHaveAsked(boolean storagePermissionHaveAsked) {
    isStoragePermissionHaveAsked = storagePermissionHaveAsked;
  }

  public long getPublicKeyGenerationTime() {
    return publicKeyGenerationTime;
  }

  public void setPublicKeyGenerationTime(long publicKeyGenerationTime) {
    this.publicKeyGenerationTime = publicKeyGenerationTime;
  }

  public String getServerConfig() {
    return serverConfig;
  }

  public void setServerConfig(String serverConfig) {
    this.serverConfig = serverConfig;
  }

  public String getAdServerConfig(){return adServerConfig;}

  public void setAdServerConfig(String adServerConfig){
    this.adServerConfig = adServerConfig;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  public String getPublicIP() {
    return publicIP;
  }

  public void setPublicIP(String publicIP) {
    this.publicIP = publicIP;
  }

  public String getSelectedRegionName() {
    return selectedRegionName;
  }

  public void setSelectedRegionName(String selectedRegionName) {
    this.selectedRegionName = selectedRegionName;
  }

  public String getSelectedLicenseStartDate() {
    return selectedLicenseStartDate;
  }

  public void setSelectedLicenseStartDate(String selectedLicenseStartDate) {
    this.selectedLicenseStartDate = selectedLicenseStartDate;
  }

  public String getSelectedLicenseExpiryDate() {
    return selectedLicenseExpiryDate;
  }

  public void setSelectedLicenseExpiryDate(String selectedLicenseExpiryDate) {
    this.selectedLicenseExpiryDate = selectedLicenseExpiryDate;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public boolean isIncludeAppsListUpdated() {
    return includeAppsListUpdated;
  }

  public void setIncludeAppsListUpdated(boolean includeAppsListUpdated) {
    this.includeAppsListUpdated = includeAppsListUpdated;
  }

  public String getUserPreferenceSelectedAppList() {
    return userPreferenceSelectedAppList;
  }

  public void setUserPreferenceSelectedAppList(String userPreferenceSelectedAppList) {
    this.userPreferenceSelectedAppList = userPreferenceSelectedAppList;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getPhoneNumber() {
    return phoneNumber != null ? phoneNumber : "";
  }

  public void setPhoneNumber(@Nullable String phoneNumber) {
    this.phoneNumber = phoneNumber != null ? phoneNumber : "";

    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString("phoneNumber", phoneNumber);
    editor.commit();
  }

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public int getVersionCode() {
    return versionCode;
  }

  public void setVersionCode(int versionCode) {
    this.versionCode = versionCode;
    System.out.println("set versionCode: - " + versionCode);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCustomerID() {
    return customerID;
  }

  public void setCustomerID(String customerID) {
    this.customerID = customerID;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCurrentIP() {
    if (currentIP == null) {
      return "";
    }
    return currentIP;
  }

  public void setCurrentIP(String currentIP) {
    this.currentIP = currentIP;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public Set<String> getSelectedApps() {
    if (selectedApps == null) selectedApps = new HashSet<>();
    return selectedApps;
  }

  public ArrayList<String> getDisallowedApps() {
    ArrayList<String> disallowedApps = new ArrayList<>();
    if (selectedApps != null) {
      disallowedApps.addAll(selectedApps);
    }
    /*if (appList != null) {
        //convert appList to List
        GetExcludeAppResponse.Result result = (GetExcludeAppResponse.Result) JsonUtil.toModel(appList, GetExcludeAppResponse.Result.class);
        if (result != null && result.getData() != null) {
            for (int k = 0; k < result.getData().size(); k++) {
                disallowedApps.remove(result.getData().get(k).getPackageName());
                //disallowedApps.add(result.getData().get(k).getPackageName());
            }
        }
    }*/

    /* if(appList != null) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(appList);
            JSONArray whiteOrBlackListedApps = jsonObject.getJSONArray("items");
            for (int i = 0; i < whiteOrBlackListedApps.length(); i++) {
                if(whiteOrBlackListedApps.getJSONObject(i).getString("package_name") != null)
                    disallowedApps.add(whiteOrBlackListedApps.getJSONObject(i).getString("package_name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
    System.out.println("disallowedApps: " + disallowedApps);
    return disallowedApps;
  }

  public void setSelectedApps(HashSet<String> selectedApps) {
    this.selectedApps = selectedApps;
  }

  public boolean isConnectedForReboot() {
    return isConnectedForReboot;
  }

  public void setConnectedForReboot(boolean connectedForReboot) {
    isConnectedForReboot = connectedForReboot;
  }

  public String getSelectedRegion() {
    return selectedRegion;
  }

  public void setSelectedRegion(String selectedRegion) {
    this.selectedRegion = selectedRegion;
  }

  public String getJsonLicense() {
    return jsonLicense;
  }

  public void setJsonLicense(String jsonLicense) {
    this.jsonLicense = jsonLicense;
  }

  public String getSelectedLicense() {
    return selectedLicense;
  }

  public void setSelectedLicense(@NonNull String selectedLicense) {
    this.selectedLicense = selectedLicense;
  }

  public String getLicenseHash() {
    return licenseHash;
  }

  public void setLicenseHash(String licenseHash) {
    this.licenseHash = licenseHash;
  }

  public boolean isFirstTime() {
    return isFirstTime;
  }

  public boolean getSeenCCPitch() {
    return seenCCPitch;
  }

  public void setSeenCCPitch(boolean seenCCPitch) {
    this.seenCCPitch = seenCCPitch;
  }

  public boolean getShowVerifyCredsDialog() {
    return showVerifyCredsDialog;
  }

  public void setFirstTime(boolean firstTime) {
    isFirstTime = firstTime;
  }

  public void setShowVerifyCredsDialog(boolean state) {
    showVerifyCredsDialog = state;
  }

  public String getAppList() {
    return appList;
  }

  public void setAppList(String appList) {
    this.appList = appList;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getName() {
    return firstName + " " + lastName;
  }

  public void setName(String name) {
    setFirstName("");
    setLastName("");
    if (name.split(" ").length > 0) setFirstName(name.split(" ")[0]);
    if (name.split(" ").length > 1) setLastName(name.split(" ")[1]);
  }

  public boolean isUserEligibleForFreeTrial() {
    return isUserEligibleForFreeTrial;
  }

  public void setUserEligibleForFreeTrial(boolean userEligibleForFreeTrial) {
    isUserEligibleForFreeTrial = userEligibleForFreeTrial;
  }

  public boolean areFeaturesFree() {
    return areFeaturesFree;
  }

  public void setAreFeaturesFree(boolean areFeaturesFree) {
    this.areFeaturesFree = areFeaturesFree;
  }

  public String getCurrentVpnConnectionWireGuardServerLink() {
    return currentVpnConnectionWireGuardServerLink;
  }

  public void setCurrentVpnConnectionWireGuardServerLink(
      String currentVpnConnectionWireGuardServerLink) {
    this.currentVpnConnectionWireGuardServerLink = currentVpnConnectionWireGuardServerLink;
  }

  public String getCurrentVpnConnectionPublicKey() {
    return currentVpnConnectionPublicKey;
  }

  public void setCurrentVpnConnectionPublicKey(String currentVpnConnectionPublicKey) {
    this.currentVpnConnectionPublicKey = currentVpnConnectionPublicKey;
  }

  public long getLastRequestedOTPAt() {
    return lastRequestedOTPAt;
  }

  public void setLastRequestedOTPAt(long lastRequestedOTPAt) {
    this.lastRequestedOTPAt = lastRequestedOTPAt;
  }

  public void setUserCountryForEvents(String userCountryForEvents) {
    this.userCountryForEvents = userCountryForEvents;
    save();
  }

  public String getUserCountryForEvents() {
    return userCountryForEvents;
  }

  //safety score prefs
  public boolean hasSeenSSOnboarding() {
    return seenSSOnboarding;
  }

  public void setSeenSSOnboarding(boolean seenSSOnboarding) {
    this.seenSSOnboarding = seenSSOnboarding;
  }

  public int getSafetyScore() {
    return safetyScore;
  }

  public void setSafetyScore(int safetyScore) {
    this.safetyScore = safetyScore;
  }

  public String getSafetyScoreScanResultJson() {
    return safetyScoreScanResultJson;
  }

  public void setSafetyScoreScanResultJson(String safetyScoreScanDataJson) {
    this.safetyScoreScanResultJson = safetyScoreScanDataJson;
  }

  public static Data getInstance(Context theContext) {
    Data newInstance = instance;
    if (newInstance == null) {
      synchronized (mutex) {
        newInstance = instance;
        if (newInstance == null) {
          instance = newInstance = new Data(theContext);
        }
      }
    }
    return newInstance;
  }

  private Data(Context context) {
    this.context = context.getApplicationContext();
    this.load();
  }

  @SuppressLint("NewApi")
  public void load() {
    // Restore preferences
    // SharedPreferences settings =
    // PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

    username = settings.getString("username", null);
    uniqueID = settings.getString("uniqueID", null);
    customerID = settings.getString("customerID", null);
    password = settings.getString("password", null);
    currentIP = settings.getString("currentIP", "");
    firstName = settings.getString("firstName", "");
    lastName = settings.getString("lastName", "");
    try {
      if (firstName == null || firstName.length() <= 0) {
        String[] names = settings.getString("name", "").split(" ");
        if (names.length > 0) firstName = names[0];
        if (names.length > 1) lastName = names[1];
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    region = settings.getString("region", "");
    isConnectedForReboot = settings.getBoolean("isConnectedForReboot", false);
    selectedRegion = settings.getString("selectedRegion", "");
    selectedApps = settings.getStringSet("selectedApps", new HashSet<>());
    jsonLicense = settings.getString("licenses", "");
    selectedLicense = settings.getString("selected_license", null);
    selectedLicenseStartDate = settings.getString("selectedLicenseStartDate", "");
    selectedLicenseExpiryDate = settings.getString("selectedLicenseExpiryDate", null);
    isFirstTime = settings.getBoolean("is_first_time", true);
    seenCCPitch = settings.getBoolean("seenCCPitch", false);
    showVerifyCredsDialog = settings.getBoolean("show_verify_creds_dialog", true);
    licenseHash = settings.getString("license_hash", "");
    appList = settings.getString("app_list", "");
    versionCode = settings.getInt("version_code", 0);
    selectedRegionName = settings.getString("selectedRegionName", "");
    email = settings.getString("email", "");
    alternateEmail = settings.getString("alternateEmail", "");
    userId = settings.getInt("userId", 0);
    phoneNumber = settings.getString("phoneNumber", "");
    alternatePhoneNumber = settings.getString("alternatePhoneNumber", "");
    languageCode = settings.getString("languageCode", "");
    deviceCurrentLanguageCode = settings.getString("deviceCurrentLanguageCode", "");
    serverConfig = settings.getString("serverConfig", "");
    adServerConfig = settings.getString("adServerConfig","");
    publicKeyGenerationTime = settings.getLong("publicKeyGenerationTime", 0);
    publicKey = settings.getString("publicKey", "");
    privateKey = settings.getString("privateKey", "");
    publicIP = settings.getString("publicIP", "");
    fastestServerCountryName = settings.getString("fastestServerCountryName", "");
    userPreferenceSelectedAppList = settings.getString("userPreferenceSelectedAppList", "");
    includeAppsListUpdated = settings.getBoolean("includeAppsListUpdated", false);
    isEmailVerified = settings.getBoolean("isEmailVerified", false);
    isPhoneVerified = settings.getBoolean("isPhoneVerified", false);
    isAltcontactAdded = settings.getBoolean("isAltcontactAdded", false);
    isAltContactVerified = settings.getBoolean("isAltContactVerified", false);
    isInsured = settings.getBoolean("isInsured", false);
    shouldShowPaymentUiInProfile = settings.getBoolean("shouldShowPaymentUiInProfile", false);
    shouldShowPaymentUiNoLicenseInProfile =
        settings.getBoolean("shouldShowPaymentUiNoLicenseInProfile", false);
    showInsuranceValidation = settings.getBoolean("showInsuranceValidation", false);
    salutation = settings.getString("salutation", "");
    isInstallRefererCalled = settings.getBoolean("isInstallRefererCalled", false);
    productType = settings.getString("productType", "");
    isUserEligibleForFreeTrial = settings.getBoolean("isUserEligibleForFreeTrial", false);
    areFeaturesFree = settings.getBoolean("areFeaturesFree", false);
    currentVpnConnectionWireGuardServerLink =
        settings.getString("currentVpnConnectionWireGuardServerLink", "");
    currentVpnConnectionPublicKey = settings.getString("currentVpnConnectionPublicKey", "");
    lastRequestedOTPAt = settings.getLong("lastRequestedOTPAt", -1L);
    userCountryForEvents = settings.getString("userCountryForEvents", "Unknown");
    seenSSOnboarding = settings.getBoolean("seenSSOnboarding", false);
    safetyScore = settings.getInt("safetyScore", 0);
    safetyScoreScanResultJson = settings.getString("safetyScoreScanDataJson", "");
  }

  @SuppressLint("NewApi")
  public void save() {

    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();

    editor.putString("username", username);
    editor.putString("uniqueID", uniqueID);
    editor.putString("customerID", customerID);
    editor.putString("password", password);
    //        editor.putString("languageCode", languageCode);
    editor.putString("deviceCurrentLanguageCode", deviceCurrentLanguageCode);
    editor.putString("currentIP", currentIP);
    editor.putString("region", region);
    editor.putBoolean("isConnectedForReboot", isConnectedForReboot);
    editor.putString("selectedRegion", selectedRegion);
    editor.putString("selectedRegionName", selectedRegionName);
    editor.putStringSet("selectedApps", new HashSet<>(selectedApps));
    editor.putString("licenses", jsonLicense);
    editor.putString("selected_license", selectedLicense);
    editor.putBoolean("is_first_time", isFirstTime);
    editor.putBoolean("seenCCPitch", seenCCPitch);
    editor.putBoolean("show_verify_creds_dialog", showVerifyCredsDialog);
    editor.putString("license_hash", licenseHash);
    editor.putString("app_list", appList);
    editor.putInt("versionCode", versionCode);
    editor.putString("selectedLicenseStartDate", selectedLicenseStartDate);
    editor.putString("selectedLicenseExpiryDate", selectedLicenseExpiryDate);
    editor.putString("email", email);
    editor.putString("alternateEmail", alternateEmail);
    editor.putInt("userId", userId);
    editor.putString("phoneNumber", phoneNumber);
    editor.putString("alternatePhoneNumber", alternatePhoneNumber);
    editor.putString("firstName", firstName);
    editor.putString("lastName", lastName);
    editor.putString("serverConfig", serverConfig);
    editor.putString("adServerConfig", adServerConfig);
    editor.putLong("publicKeyGenerationTime", publicKeyGenerationTime);
    editor.putString("publicKey", publicKey);
    editor.putString("privateKey", privateKey);
    editor.putString("publicIP", publicIP);
    editor.putString("fastestServerCountryName", fastestServerCountryName);
    editor.putString("userPreferenceSelectedAppList", userPreferenceSelectedAppList);
    editor.putBoolean("includeAppsListUpdated", includeAppsListUpdated);
    editor.putBoolean("isPhoneVerified", isPhoneVerified);
    editor.putBoolean("isAltcontactAdded", isAltcontactAdded != null && isAltcontactAdded);
    editor.putBoolean("isAltContactVerified", isAltContactVerified != null && isAltContactVerified);
    editor.putBoolean("isInsured", isInsured != null && isInsured);
    editor.putBoolean(
        "shouldShowPaymentUiInProfile",
        shouldShowPaymentUiInProfile != null && shouldShowPaymentUiInProfile);
    editor.putBoolean(
        "shouldShowPaymentUiNoLicenseInProfile",
        shouldShowPaymentUiNoLicenseInProfile != null && shouldShowPaymentUiNoLicenseInProfile);
    editor.putBoolean(
        "showInsuranceValidation", showInsuranceValidation != null && showInsuranceValidation);
    editor.putString("salutation", salutation);
    editor.putBoolean("isEmailVerified", isEmailVerified);
    editor.putBoolean("isInstallRefererCalled", isInstallRefererCalled);
    editor.putBoolean("isUserEligibleForFreeTrial", isUserEligibleForFreeTrial);
    editor.putBoolean("areFeaturesFree", areFeaturesFree);
    editor.putString(
        "currentVpnConnectionWireGuardServerLink", currentVpnConnectionWireGuardServerLink);
    editor.putString("currentVpnConnectionPublicKey", currentVpnConnectionPublicKey);
    editor.putLong("lastRequestedOTPAt", lastRequestedOTPAt);
    editor.putString("productType", productType);
    editor.putString("userCountryForEvents", userCountryForEvents);
    editor.putBoolean("seenSSOnboarding", seenSSOnboarding);
    editor.putInt("safetyScore", safetyScore);
    editor.putString("safetyScoreScanDataJson", safetyScoreScanResultJson);
    editor.commit();
  }

  public Data resetData() {
    String user = getEmail() != null ? getEmail() : getPhoneNumber();
    String email = getEmail();
    String phone = getPhoneNumber();
    String alternateEmail = getAlternateEmail();
    String alternatePhoneNumber = getAlternatePhoneNumber();
    String languageCode = getLanguageCode();
    boolean isInstallRefererCalled = isInstallRefererCalled();
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.clear();
    editor.apply();
    instance = null;
    Data newDataInstance = getInstance(context);
    newDataInstance.setUsername(user);
    newDataInstance.setEmail(email);
    newDataInstance.setPhoneNumber(phone);
    newDataInstance.setEmail(alternateEmail);
    newDataInstance.setPhoneNumber(alternatePhoneNumber);
    newDataInstance.setLanguageCode(languageCode);
    newDataInstance.setInstallRefererCalled(isInstallRefererCalled);
    newDataInstance.save();
    return newDataInstance;
  }

  public String getBestRegionCode() {
    return bestRegionCode;
  }

  public void setBestRegionCode(String bestRegionCode) {
    this.bestRegionCode = bestRegionCode;
  }

  public void setBooleanData(String key, Boolean value) {
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }

  public Boolean getBooleanData(String key) {
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    return settings.getBoolean(key, false);
  }

  public Boolean getPositiveBooleanData(String key) {
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    return settings.getBoolean(key, true);
  }

  public void setIntData(String key, Integer value) {
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putInt(key, value);
    editor.apply();
  }

  public Integer getIntData(String key) {
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    return settings.getInt(key, 0);
  }

  public void setStringData(String key, String value) {
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(key, value);
    editor.apply();
  }

  public String getStringData(String key) {
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    return settings.getString(key, "");
  }

  public void setBooleanData(String tag, boolean val) {
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putBoolean(tag, val);
    editor.apply();
  }

  public boolean isAltcontactAdded() {
    return isAltcontactAdded != null && isAltcontactAdded;
  }

  public void setAltcontactAdded(Boolean altcontactAdded) {
    isAltcontactAdded = altcontactAdded;
  }

  public String getAlternateEmail() {
    return alternateEmail;
  }

  public void setAlternateEmail(String alternateEmail) {
    this.alternateEmail = alternateEmail;
  }

  public String getAlternatePhoneNumber() {
    return alternatePhoneNumber;
  }

  public void setAlternatePhoneNumber(String alternatePhoneNumber) {
    this.alternatePhoneNumber = alternatePhoneNumber;
  }

  public boolean isAltContactVerified() {
    return isAltContactVerified != null && isAltContactVerified;
  }

  public void setAltContactVerified(Boolean altContactVerified) {
    isAltContactVerified = altContactVerified;
  }

  public int getShowOnboardingCount() {
    return showOnboardingCount;
  }

  public void setShowOnboardingCount(int showOnboardingCount) {
    this.showOnboardingCount = showOnboardingCount;
  }
}
