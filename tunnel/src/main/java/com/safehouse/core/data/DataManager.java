package com.safehouse.core.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Set;
import javax.annotation.Nullable;

public interface DataManager {

  // App state management
  String getFirstOpenDate();

  void saveFirstOpenDate();

  int getAppOpenCount();

  boolean isAppNotUpdated();

  void resetRateDialogFirstOpen();

  boolean checkAntiTheftOn();

  //// app language - part of app state management
  void saveLangCodeUpdateLocale();

  String getLanguageCode();

  void setLanguageCode(String langCode);

  //// apps list - part of app state management
  ArrayList<String> getDisallowedApps();

  Set<String> getSelectedApps();

  boolean hasUpdatingApps();

  void setHasUpdatingApps(boolean state);

  //// feature check - part of app state management
  boolean isAntiTheftActive();

  boolean isLinkWatchActive(Context context);

  void setLinkWatchActive(boolean isActive);

  boolean isGeoLocatorActive();

  boolean isBreachActive();

  boolean isAppLockActive();

  String getAppLockPin();

  //// dialogs visibility - part of app state management
  boolean canShowRateDialog();

  void shouldShowRateDialog(boolean status);

  boolean hasShownRateDialog();

  void setRateDialogShown(boolean status);

  boolean canShowTrackerDialog();

  void setShouldShowTrackerDialog(boolean state);

  boolean canShowVerifyCredsDialog();

  void setShowVerifyCredsDialog(boolean state);

  //// notifications handling - part of app state management

  String getSavedFcmToken();

  String getMixpanelToken();

  void saveAndPushMixpanelToken(String token);

  // User State Management
  boolean getIsInsured();

  void setIsInsured(boolean insured);

  boolean shouldShowLicenseExpiredUIinProfile();

  void setShouldShowLicenseExpiredUIinProfile(boolean shouldShowLicenseExpiredInProfile);

  boolean shouldShowLicenseNoneUIinProfile();

  void setShouldShowLicenseNoneUIinProfile(boolean shouldShowLicenseNoneInProfile);

  boolean getShowInsuranceValidation();

  void setShowInsuranceValidation(boolean showInsuranceValidation);

  String getSalutation();

  void setSalutation(String salutation);

  String getToken();

  boolean isTokenAvailable();

  void saveDynamicLinkTokenAndOTP(String token, String otp);

  void saveIsPhoneVerified(Boolean isPhoneVerified);

  void saveIsAltContactAdded(Boolean isAltContactAdded);

  void saveIsAltContactVerified(Boolean isAltContactverified);

  void saveIsEmailVerified(boolean isEmailVerified);

  String getDynamicLinkUserOTP();

  void saveToken(String token);

  void saveRefreshToken(String refreshToken);

  void saveUserNameAndContact(String username, String phoneNumber);

  String getUsername();

  String getPassword();

  void resetData();

  void saveName(String name);

  void saveEmail(String email);

  void savePhoneNumber(String phone);

  void saveAlternateEmail(String email);

  void saveAlternatePhoneNumber(String phone);

  void saveUniqueID(String uniqueId);

  // App Updates Management
  void saveVersionCode();

  boolean isUpdateByOldServerUser();

  // License Management
  void saveLicense(String license);

  boolean setLicenseExpiryDate(String expiryDate);

  String getJsonLicense();

  String getSelectedLicense();

  boolean isExpiryDateEmptyForSelectedLicense();

  void setLicenseStartDate(String startDate);

  String getLicenseStartDate();

  String getLicenseExpiryDate();

  // VPN state management
  void setVpnStartTime(long startTime);

  long getVpnStartTime();

  void setConnectedForReboot(boolean status);

  String getPublicIp();

  void setPublicIp(String ipAddress);

  void setAppList(String listAsString);

  //// location ops
  String getSelectedRegion();

  void setSelectedRegion(String region, String regionName);

  String getSelectedRegionName();

  void setFasterServerCountry(String countryName);

  String getFastestServerCountryName();

  void sendFirstLastConnectionData();

  // Data
  Data getData(); // must be replaced - should not expose data class to other classes - violates
  // single responsibility (tech debt)

  void saveAlreadySetData();

  // Miscellaneous
  String getProductType();

  void logout();

  String getName();

  boolean isPhoneVerified();

  boolean isAltContactAdded();

  boolean isEmailVerified();

  void setFreeFeatureState(boolean state);

  void setFreeTrialStates(boolean isUserEligibleForTrial, boolean freeFeaturesState);

  boolean getIfUserEligibleState();

  boolean getFeaturesFreeState();

  // token manager
  TokenManager getTokenManager();

  // Active Defence State Management
  void setADOverlayShown(boolean state);

  boolean getADOverlayShown();

  void setOverrideAndShowAD(boolean status);

  boolean getOverrideAndShowAD();

  // safety score
  // todo(Siva): make sure its connected with FireStore as well for confirmation
  Boolean seenSSOnboarding(); // default it will be false

  void setSeenSSOnboarding(boolean seenSSOnboarding);

  int getSafetyScore(); // default it will be false

  void setSafetyScore(int safetyScore);

  String getSafetyScoreScanResultJson(); // default it will be false

  void setSafetyScoreScanResultJson(String safetyScoreScanDataJson);

//  @Nullable SafetyScoreModel getSafetyScoreModel();

  // first scan
  void setSSFirstScanOccurred(boolean status);

  Boolean hasSSFirstScanOccurred();
}
