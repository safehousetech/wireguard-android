package com.safehouse.core.data;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.safehouse.core.util.Config;
import com.safehouse.core.util.DateHelper;
import java.util.ArrayList;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDataManager implements DataManager {

  private static final String TAG = "AppDataManager";
  private final Context appContext;
  private final Config config;
//  private final FirebaseAnalytics firebaseAnalytics;

  private Data data;
  // TODO: we'd have to manage tokenManager separate from dataManager
  // The changes would include not linking tokenManager with DataManager anywhere.
  private TokenManager tokenManager;

  @Inject
  public AppDataManager(
      Context appContext,
      Config config,
      Data data,
      TokenManager tokenManager
//      FirebaseAnalytics firebaseAnalytics
  ) {
    this.appContext = appContext;
    this.data = data;
    this.tokenManager = tokenManager;
    this.config = config;
//    this.firebaseAnalytics = firebaseAnalytics;
  }

  @Override
  public TokenManager getTokenManager() {
    return tokenManager;
  }

  @Override
  public void setADOverlayShown(boolean state) {
    config.setADOverlayShown(state);
  }

  @Override
  public boolean getADOverlayShown() {
    return config.getADOverlayShown();
  }

  @Override
  public void setOverrideAndShowAD(boolean status) {
    config.setOverrideAndShowAD(status);
  }

  @Override
  public boolean getOverrideAndShowAD() {
    return config.getOverrideAndShowAD();
  }

  @Override
  public String getFirstOpenDate() {
    return config.getFirstOpenDate();
  }

  @Override
  public void saveFirstOpenDate() {
    if (getFirstOpenDate().equals("")) {
      config.setFirstOpenDate(DateHelper.getCurrentDate());
    }
  }

  @Override
  public int getAppOpenCount() {
    return config.appOpenCount();
  }

  @Override
  public String getToken() {
    return tokenManager.getToken();
  }

  @Override
  public boolean isTokenAvailable() {
    return !tokenManager.getToken().isEmpty();
  }

  @Override
  public boolean isAppNotUpdated() {
//    return config.getVersionCode() < BuildConfig.VERSION_CODE;
    return false;
  }

  @Override
  public void setShouldShowTrackerDialog(boolean state) {
    config.setToShowTrackerDialog(state);
  }

  @Override
  public boolean canShowVerifyCredsDialog() {
    return data.getShowVerifyCredsDialog();
  }

  @Override
  public void setShowVerifyCredsDialog(boolean state) {
    data.setShowVerifyCredsDialog(state);
  }

  @Override
  public void setRateDialogShown(boolean status) {
    config.setHasShownRateDialog(status);
  }

  @Override
  public void resetRateDialogFirstOpen() {
    config.setFirstOpenDate(DateHelper.getCurrentDate());
    config.resetAppOpenCount();
  }

  @Override
  public boolean checkAntiTheftOn() {
    return data.getBooleanData(data.ANTI_THEFT_SNAP_SHOT_ACTIVE);
  }

  @Override
  public String getLanguageCode() {
    return data.getLanguageCode();
  }

  @Override
  public void setLanguageCode(String langCode) {
    data.setLanguageCode(langCode);
    saveAlreadySetData();
  }

  @Override
  public String getSavedFcmToken() {
    return tokenManager.getFcmToken();
  }

  @Override
  public String getMixpanelToken() {
    return tokenManager.getMixpanelToken();
  }

  @Override
  public void saveAndPushMixpanelToken(String token) {
    tokenManager.setMixpanelToken(token);
  }

  @Override
  public boolean getIsInsured() {
    return data.isInsured();
  }

  @Override
  public void setIsInsured(boolean insured) {
    data.setIsInsured(insured);
    saveAlreadySetData();
  }

  @Override
  public boolean shouldShowLicenseExpiredUIinProfile() {
    return data.shouldShowPaymentUIInProfileScreen();
  }

  @Override
  public void setShouldShowLicenseExpiredUIinProfile(boolean shouldShowLicenseExpiredInProfile) {
    data.setShouldShowPaymentUIInProfileScreen(shouldShowLicenseExpiredInProfile);
    saveAlreadySetData();
  }

  @Override
  public boolean shouldShowLicenseNoneUIinProfile() {
    return data.shouldShowPaymentUINoLicenseInProfileScreen();
  }

  @Override
  public void setShouldShowLicenseNoneUIinProfile(boolean shouldShowLicenseNoneInProfile) {
    data.setShouldShowPaymentUINoLicenseInProfileScreen(shouldShowLicenseNoneInProfile);
    saveAlreadySetData();
  }

  @Override
  public boolean getShowInsuranceValidation() {
    return data.getShowInsuranceValidation();
  }

  @Override
  public void setShowInsuranceValidation(boolean showInsuranceValidation) {
    data.setShowInsuranceValidation(showInsuranceValidation);
    saveAlreadySetData();
  }

  @Override
  public String getSalutation() {
    return data.getSalutation();
  }

  @Override
  public void setSalutation(String salutation) {
    data.setSalutation(salutation);
  }

  @Override
  public ArrayList<String> getDisallowedApps() {
    return data.getDisallowedApps();
  }

  @Override
  public Set<String> getSelectedApps() {
    return data.getSelectedApps();
  }

  @Override
  public boolean hasUpdatingApps() {
    return config.hasUpdatingApps();
  }

  @Override
  public void setHasUpdatingApps(boolean state) {
    config.setHasUpdatingApps(state);
  }

  @Override
  public boolean isAntiTheftActive() {
    return config.isAntiTheftActive();
  }

  @Override
  public boolean isLinkWatchActive(Context context) {
    return config.isLinkWatchActive(context);
  }

  @Override
  public void setLinkWatchActive(boolean isActive) {
    config.setIsLinkWatchActive(isActive);
  }

  @Override
  public boolean isGeoLocatorActive() {
    return config.isGeoLocatorActive();
  }

  @Override
  public boolean isBreachActive() {
    return config.isBreachActive();
  }

  @Override
  public boolean isAppLockActive() {
    return config.isAppLockActive();
  }

  @Override
  public String getAppLockPin() {
    return config.getAppLockPin();
  }

  @Override
  public boolean canShowRateDialog() {
    return config.canShowRateDialog();
  }

  @Override
  public void shouldShowRateDialog(boolean status) {
    config.canShowRateDialog(status);
  }

  @Override
  public boolean hasShownRateDialog() {
    return config.hasShownRateDialog();
  }

  @Override
  public boolean canShowTrackerDialog() {
    return config.canShowTrackerDialog();
  }

  @Override
  public void saveDynamicLinkTokenAndOTP(String token, String otp) {
    setDynamicLinkToken(token);
    setOTPFromDynamicLink(otp);
    saveAlreadySetData();
  }

  @Override
  public void saveIsPhoneVerified(Boolean isPhoneVerified) {
    setIsPhoneVerified(isPhoneVerified);
    saveAlreadySetData();
  }

  @Override
  public void saveIsAltContactAdded(Boolean isAltContactAdded) {
    data.setAltcontactAdded(isAltContactAdded);
    saveAlreadySetData();
  }

  @Override
  public void saveIsAltContactVerified(Boolean isAltContactVerified) {
    data.setAltContactVerified(isAltContactVerified);
    saveAlreadySetData();
  }

  @Override
  public void saveIsEmailVerified(boolean isEmailVerified) {
    setIsEmailVerified(isEmailVerified);
    saveAlreadySetData();
  }

  @Override
  public String getDynamicLinkUserOTP() {
    return tokenManager.getVerifyUserOtp();
  }

  @Override
  public void saveToken(String token) {
    setToken(token);
    saveAlreadySetData();
  }

  @Override
  public void saveRefreshToken(String refreshToken) {
    tokenManager.setRefreshToken(refreshToken);
    saveAlreadySetData();
  }

  @Override
  public void saveUserNameAndContact(String username, String phoneNumber) {
    setUsername(username);
    setPhoneNumber(phoneNumber);
    saveAlreadySetData();
  }

  @Override
  public String getUsername() {
    return data.getUsername();
  }

  @Override
  public String getPassword() {
    return data.getPassword();
  }

  @Override
  public void resetData() {
    String deviceIDString = data.getStringData(Data.DEVICE_SERVER_ID);
    data = data.resetData();
    data.setStringData(Data.DEVICE_SERVER_ID, deviceIDString);
  }

  @Override
  public void saveName(@NonNull String name) {
    if (name.isEmpty()) {
      data.setFirstName("");
      data.setLastName("");
    } else {
      String[] splitNames = name.split(" ");
      if (splitNames.length > 0) {
        data.setFirstName(name.split(" ")[0]);
        if (splitNames.length > 1) {
          data.setLastName(name.split(" ")[1]);
        }
      }
    }
    saveAlreadySetData();
  }

  @Override
  public void saveEmail(String email) {
    data.setEmail(email);
    saveAlreadySetData();
  }

  @Override
  public void savePhoneNumber(String phone) {
    data.setPhoneNumber(phone);
    saveAlreadySetData();
  }

  @Override
  public void saveAlternateEmail(String email) {
    data.setAlternateEmail(email);
    saveAlreadySetData();
  }

  @Override
  public void saveAlternatePhoneNumber(String phone) {
    data.setAlternatePhoneNumber(phone);
    saveAlreadySetData();
  }

  @Override
  public void saveUniqueID(String uniqueId) {
    data.setUniqueID(uniqueId);
    saveAlreadySetData();
  }

  @Override
  public void saveLicense(String license) {
    setLicense(license);
    saveAlreadySetData();
    System.out.println("test: = saveLicense");
  }

  @Override
  public void saveVersionCode() {
    config.setVersionCode(999);
  }

  @Override
  public boolean setLicenseExpiryDate(String expiryDate) {
    if (data.getSelectedLicense() != null) {
      if (!TextUtils.isEmpty(expiryDate)) {
        data.setSelectedLicenseExpiryDate(expiryDate);
      } else {
        data.setSelectedLicenseExpiryDate(data.getSelectedLicenseExpiryDate());
      }
      data.save();
      return true; // data saved
    }
    return false; // function didn't get performed
  }

  @Override
  public String getJsonLicense() {
    return data.getJsonLicense();
  }

  @Override
  public String getSelectedLicense() {
    return data.getSelectedLicense();
  }

  @Override
  public Data getData() {
    return data;
  }

  @Override
  public void saveAlreadySetData() {
    data.save();
    System.out.println("test: = saveAlreadySetData");
  }

  @Override
  public boolean isUpdateByOldServerUser() {
    boolean oldUserUpdate = config.getVersionCode() > 0 && config.getVersionCode() <= 223
            || isExpiryDateEmptyForSelectedLicense();
    System.out.println("SafeHouse :- " + oldUserUpdate);
    return oldUserUpdate;
  }

  @Override
  public boolean isExpiryDateEmptyForSelectedLicense() {
    return !TextUtils.isEmpty(data.getSelectedLicense())
        && TextUtils.isEmpty(data.getSelectedLicenseExpiryDate());
  }

  @Override
  public void setLicenseStartDate(String startDate) {
    data.setSelectedLicenseStartDate(startDate);
  }

  @Override
  public String getLicenseStartDate() {
    return data.getSelectedLicenseStartDate();
  }

  @Override
  public String getLicenseExpiryDate() {
    return data.getSelectedLicenseExpiryDate();
  }

  @Override
  public void setVpnStartTime(long startTime) {
    config.setVPNStartTime(startTime);
  }

  @Override
  public long getVpnStartTime() {
    return config.getVPNStartTime();
  }

  @Override
  public void setConnectedForReboot(boolean status) {
    data.setConnectedForReboot(true);
    data.save();
  }

  @Override
  public String getPublicIp() {
    return data.getPublicIP();
  }

  @Override
  public void setPublicIp(String ipAddress) {
    data.setPublicIP(ipAddress);
    data.save();
  }

  @Override
  public void setAppList(String includeAppsList) {
    data.setAppList(includeAppsList);
    saveAlreadySetData();
  }

  @Override
  public String getSelectedRegion() {
    return data.getSelectedRegion();
  }

  @Override
  public void setSelectedRegion(String region, String regionName) {
    data.setSelectedRegion(region);
    data.setSelectedRegionName(regionName);
    saveAlreadySetData();
  }

  @Override
  public String getSelectedRegionName() {
    return data.getSelectedRegionName();
  }

  @Override
  public void setFasterServerCountry(String countryName) {
    data.setFastestServerCountryName(countryName);
    saveAlreadySetData();
  }

  @Override
  public String getFastestServerCountryName() {
    return data.getFastestServerCountryName();
  }

  @Override
  public void saveLangCodeUpdateLocale() {
    String savedLanguageCode = data.getLanguageCode();
    if (TextUtils.isEmpty(savedLanguageCode)) {
      String locale = appContext.getResources().getConfiguration().locale.getLanguage();
      if ("iw".equalsIgnoreCase(locale) || "he".equalsIgnoreCase(locale)) {
        data.setLanguageCode(locale);
        data.save();
//        LocaleManager.updateResources(appContext, data.getLanguageCode());
      }
    }
  }

  @Override
  public String getProductType() {
    return data.getProductType();
  }

  @Override
  public void logout() {
    data.setBooleanData(data.ANTI_THEFT_SNAP_SHOT_ACTIVE, false);
    data.setBooleanData(Data.LOCATION_ALARM_ALREADY_RUN, false);
    data = data.resetData();

    boolean hasSeenEula = config.hasSeenEula();
    config.clear();
    config.setSeenEula(hasSeenEula);
  }

  @Override
  public String getName() {
    return (data.getFirstName() + " " + data.getLastName()).trim();
  }

  @Override
  public boolean isPhoneVerified() {
    return data.isPhoneVerified();
  }

  @Override
  public boolean isAltContactAdded() {
    return data.isAltcontactAdded();
  }

  @Override
  public boolean isEmailVerified() {
    return data.isEmailVerified();
  }

  @Override
  public void setFreeFeatureState(boolean state) {
    data.setAreFeaturesFree(state);
    data.save();
  }

  @Override
  public void setFreeTrialStates(boolean isUserEligibleForTrial, boolean freeFeaturesState) {
    data.setUserEligibleForFreeTrial(isUserEligibleForTrial);
    data.setAreFeaturesFree(freeFeaturesState);
    data.save();
  }

  @Override
  public boolean getIfUserEligibleState() {
    return data.isUserEligibleForFreeTrial();
  }

  @Override
  public boolean getFeaturesFreeState() {
    return data.areFeaturesFree();
  }

  @Override
  public void sendFirstLastConnectionData() {
    if (TextUtils.isEmpty(config.firstVPN())) {
      config.firstVPN(DateHelper.getCurrentDate());
    }
  }

  @Override
  public Boolean seenSSOnboarding() {
    return data.hasSeenSSOnboarding();
  }

  @Override
  public void setSeenSSOnboarding(boolean seenSSOnboarding) {
    data.setSeenSSOnboarding(seenSSOnboarding);
    data.save();
  }

  @Override
  public int getSafetyScore() {
    return data.getSafetyScore();
  }

  @Override
  public void setSafetyScore(int safetyScore) {
    data.setSafetyScore(safetyScore);
    data.save();
  }

  @Override
  public String getSafetyScoreScanResultJson() {
    return data.getSafetyScoreScanResultJson();
  }

  @Override
  public void setSafetyScoreScanResultJson(String safetyScoreScanResultJson) {
    data.setSafetyScoreScanResultJson(safetyScoreScanResultJson);
    data.save();
  }

//  @Override
//  @Nullable public SafetyScoreModel getSafetyScoreModel() {
//    try {
//      Gson gson = new Gson();
//      return gson.fromJson(getSafetyScoreScanResultJson(), SafetyScoreModel.class);
//    } catch (Exception e) {
//      e.printStackTrace();
//      return null;
//    }
//  }

  @Override
  public void setSSFirstScanOccurred(boolean status) {
    config.setHasSSFirstScanOccurred(status);
  }

  @Override
  public Boolean hasSSFirstScanOccurred() {
    return config.getHasSSFirstScanOccurred();
  }

  private void setLicense(String license) {
    data.setJsonLicense(license);
  }

  private void setUserEmail(String userEmail) {
    data.setEmail(userEmail);
  }

  private void setToken(String token) {
    tokenManager.setToken(token);
  }

  private void setDynamicLinkToken(String token) {
    tokenManager.setDynamicLinkToken(token);
  }

  private void setOTPFromDynamicLink(String otp) {
    tokenManager.setVerifyUserOtp(otp);
  }

  private void setIsPhoneVerified(boolean isPhoneVerified) {
    data.setPhoneVerified(isPhoneVerified);
  }

  private void setIsEmailVerified(boolean isEmailVerified) {
    data.setEmailVerified(isEmailVerified);
  }

  private void setPhoneNumber(String phoneNumber) {
    data.setPhoneNumber(phoneNumber);
  }

  private void setUsername(String username) {
    data.setUsername(username);
  }

  private void setName(String name) {
    data.setFirstName("");
    data.setLastName("");
    String[] splitNames = name.split(" ");
    if (splitNames.length > 0) {
      data.setFirstName(name.split(" ")[0]);
      if (splitNames.length > 1) {
        data.setLastName(name.split(" ")[1]);
      }
    }
  }

  private void setPassword(String password) {
    data.setPassword(password);
  }
}
