package com.safehouse.core.util;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import javax.annotation.Nonnull;

public class Config {
  private final String TAG = Config.class.getSimpleName();
  public static final String PREF_NAME = "shdemo";
  private SharedPreferences _pref;

  public Config(@Nonnull Context context) {
    _pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
  }

  /** This Method Clear shared preference. */
  public void clear() {
    SharedPreferences.Editor editor = _pref.edit();
    editor.clear();
    editor.apply();
  }

  public void setVersionCode(int id) {
    PreferenceUtil.setInt(_pref, Key.versionCode, id);
  }

  public int getVersionCode() {
    return PreferenceUtil.getInt(_pref, Key.versionCode, 0);
  }

  public void setVPNStartTime(long startTime) {
    PreferenceUtil.setLong(_pref, Key.vpnStartTime, startTime);
  }

  public long getVPNStartTime() {
    return PreferenceUtil.getLong(_pref, Key.vpnStartTime, 0L);
  }

  public void setAppLockStatus(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.appLockActivateStatus, status);
  }

  public boolean isAppLockActive() {
    return PreferenceUtil.getBoolean(_pref, Key.appLockActivateStatus, false);
  }

  public boolean isAntiTheftActive() {
    return PreferenceUtil.getBoolean(_pref, Key.antiTheftActivateStatus, false);
  }

  public void setAntiTheftStatus(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.antiTheftActivateStatus, status);
  }

  public void setIsActivatingAppLock(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.isActivatingAppLock, status);
  }

  public boolean isActivatingAppLock() {
    return PreferenceUtil.getBoolean(_pref, Key.isActivatingAppLock, false);
  }

  public void setIsActivatingTracker(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.isActivatingTracker, status);
  }

  public boolean isActivatingTracker() {
    return PreferenceUtil.getBoolean(_pref, Key.isActivatingTracker, false);
  }

  public void setHasSeenAppLockBeta(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.hasSeenAppLockBeta, status);
  }

  public boolean hasSeenAppLockBeta() {
    return PreferenceUtil.getBoolean(_pref, Key.hasSeenAppLockBeta, false);
  }

  public void setHasSeenAppLockDialog(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.hasSeenAppLockDialog, status);
  }

  public boolean hasSeenAppLockDialog() {
    return PreferenceUtil.getBoolean(_pref, Key.hasSeenAppLockDialog, false);
  }

  public boolean hasSeenAntiTheftDialog() {
    return PreferenceUtil.getBoolean(_pref, Key.hasSeenAntiTheftDialog, false);
  }

  public void setHasSeenAntiTheftDialog(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.hasSeenAntiTheftDialog, status);
  }

  public boolean hasSeenBreachDialog() {
    return PreferenceUtil.getBoolean(_pref, Key.hasSeenBreachDialog, false);
  }

  public void setHasSeenBreachDialog(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.hasSeenBreachDialog, status);
  }

  public void setAppLockPin(String status) {
    PreferenceUtil.setString(_pref, Key.appLockPin, status);
  }

  public String getAppLockPin() {
    return PreferenceUtil.getString(_pref, Key.appLockPin, "");
  }

  public String getCurrentForegroundApp() {
    return PreferenceUtil.getString(_pref, Key.currentForegroundApp, "");
  }

  public String getFirstOpenDate() {
    return PreferenceUtil.getString(_pref, Key.firstOpenDate, "");
  }

  public void setFirstOpenDate(String date) {
    PreferenceUtil.setString(_pref, Key.firstOpenDate, date);
  }

  public void setCurrentForegroundApp(String status) {
    PreferenceUtil.setString(_pref, Key.currentForegroundApp, status);
  }

  public void setFingerPrintEnabled(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.enableFingerPrint, status);
  }

  public boolean isFingerPrintEnabled() {
    return PreferenceUtil.getBoolean(_pref, Key.enableFingerPrint, false);
  }

  public void setHasAskedAutoStart(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.hasAskedAutoStart, status);
  }

  public boolean hasAskedAutoStart() {
    return PreferenceUtil.getBoolean(_pref, Key.hasAskedAutoStart, false);
  }

  public void setLockAppsPerSession(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.lockAppsPerSession, status);
  }

  public boolean lockAppsPerSession() {
    return PreferenceUtil.getBoolean(_pref, Key.lockAppsPerSession, true);
  }

  public void setHasSeenGeoLocator(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.hasSeenGeoLocator, status);
  }

  public boolean hasSeenGeoLocator() {
    return PreferenceUtil.getBoolean(_pref, Key.hasSeenGeoLocator, false);
  }

  public void setHasUpdatingApps(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.hasUpdatingApps, status);
  }

  public boolean hasUpdatingApps() {
    return PreferenceUtil.getBoolean(_pref, Key.hasUpdatingApps, true);
  }

  public void setIsLinkWatchActive(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.isLinkWatchActive, status);
  }

  public boolean isLinkWatchActive(Context context) {
    boolean isDefaultOn12 = false;
    if (Build.VERSION_CODES.R < Build.VERSION.SDK_INT) {
      RoleManager roleManager = (RoleManager) context.getSystemService(Context.ROLE_SERVICE);
      if (roleManager.isRoleAvailable(RoleManager.ROLE_BROWSER))
        if (roleManager.isRoleHeld(RoleManager.ROLE_BROWSER)) isDefaultOn12 = true;
    } else isDefaultOn12 = true;
    return isDefaultOn12 && PreferenceUtil.getBoolean(_pref, Key.isLinkWatchActive, false);
  }

  public void setIsGeoLocatorActive(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.isGeoLocatorActive, status);
  }

  public boolean isGeoLocatorActive() {
    return PreferenceUtil.getBoolean(_pref, Key.isGeoLocatorActive, false);
  }

  public void setIsBreachActive(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.isBreachActive, status);
  }

  public boolean isBreachActive() {
    return PreferenceUtil.getBoolean(_pref, Key.isBreachActive, false);
  }

  public void firstVPN(String date) {
    PreferenceUtil.setString(_pref, Key.firstVPN, date);
  }

  public String firstVPN() {
    return PreferenceUtil.getString(_pref, Key.firstVPN, "");
  }

  public void firstTracker(String date) {
    PreferenceUtil.setString(_pref, Key.firstTracker, date);
  }

  public String firstTracker() {
    return PreferenceUtil.getString(_pref, Key.firstTracker, "");
  }

  public void firstPatriot(String date) {
    PreferenceUtil.setString(_pref, Key.firstPatriot, date);
  }

  public String firstPatriot() {
    return PreferenceUtil.getString(_pref, Key.firstPatriot, "");
  }

  public void firstThreat(String date) {
    PreferenceUtil.setString(_pref, Key.firstThreat, date);
  }

  public String firstThreat() {
    return PreferenceUtil.getString(_pref, Key.firstThreat, "");
  }

  public void firstAirtight(String date) {
    PreferenceUtil.setString(_pref, Key.firstAirtight, date);
  }

  public String firstAirtight() {
    return PreferenceUtil.getString(_pref, Key.firstAirtight, "");
  }

  public String firstPopup() {
    return PreferenceUtil.getString(_pref, Key.firstPopup, "");
  }

  public void increaseAppOpenCount() {
    int openCount = appOpenCount();
    PreferenceUtil.setInt(_pref, Key.appOpenCount, ++openCount);
  }

  public void resetAppOpenCount() {
    PreferenceUtil.setInt(_pref, Key.appOpenCount, 0);
  }

  public int appOpenCount() {
    return PreferenceUtil.getInt(_pref, Key.appOpenCount, 0);
  }

  public void setHasShownRateDialog(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.hasShownRateDialog, status);
  }

  public boolean hasShownRateDialog() {
    return PreferenceUtil.getBoolean(_pref, Key.hasShownRateDialog, false);
  }

  public void canShowRateDialog(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.canShowRateDialog, status);
  }

  public boolean canShowRateDialog() {
    return PreferenceUtil.getBoolean(_pref, Key.canShowRateDialog, false);
  }

  public void setToShowTrackerDialog(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.showTrackerDialog, status);
  }

  public boolean canShowTrackerDialog() {
    return PreferenceUtil.getBoolean(_pref, Key.showTrackerDialog, false);
  }

  public boolean hasSeenEula() {
    return PreferenceUtil.getBoolean(_pref, Key.seenEula, false);
  }

  public void setSeenEula(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.seenEula, status);
  }

  public boolean hasSeenRYTutorial() {
    return PreferenceUtil.getBoolean(_pref, Key.seenRYTutorial, false);
  }

  public void setSeenRYTutorial(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.seenRYTutorial, status);
  }

  public void setADOverlayShown(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.seenADPermOverlay, status);
  }

  public boolean getADOverlayShown() {
    return PreferenceUtil.getBoolean(_pref, Key.seenADPermOverlay, false);
  }

  public void setOverrideAndShowAD(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.overrideAndShowAD, status);
  }

  public boolean getOverrideAndShowAD() {
    return PreferenceUtil.getBoolean(_pref, Key.overrideAndShowAD, false);
  }

  public void setHasSSFirstScanOccurred(boolean status) {
    PreferenceUtil.setBoolean(_pref, Key.hasSSFirstScanOccurred, status);
  }

  public boolean getHasSSFirstScanOccurred() {
    return PreferenceUtil.getBoolean(_pref, Key.hasSSFirstScanOccurred, false);
  }

  private final class Key {
    public static final String versionCode = "versionCode";
    public static final String vpnStartTime = "vpn_start_time";
    public static final String isActivatingAppLock = "isActivatingApplock";
    public static final String isActivatingTracker = "isActivatingTracker";
    public static final String hasSeenAppLockBeta = "hasSeenAppLockBeta";
    public static final String hasSeenAppLockDialog = "hasSeenAppLockDialog";
    public static final String appLockActivateStatus = "appLockActivateStatus";
    public static final String antiTheftActivateStatus = "antiTheftActivateStatus";
    public static final String currentForegroundApp = "currentForegroundApp";
    public static final String enableFingerPrint = "enableFingerPrint";
    public static final String appLockPin = "appLockPin";
    public static final String hasAskedAutoStart = "hasAskedAutoStart";
    public static final String lockAppsPerSession = "lockAppsPerSession";
    public static final String hasSeenGeoLocator = "hasSeenGeoLocator";
    public static final String hasUpdatingApps = "hasUpdatingApps";
    public static final String isLinkWatchActive = "isLinkWatchActive";
    public static final String isGeoLocatorActive = "isGeoLocatorActive";
    public static final String isBreachActive = "isBreachActive";
    public static final String hasSeenAntiTheftDialog = "hasSeenAntiTheftDialog";
    public static final String hasSeenBreachDialog = "hasSeenBreachDialog";
    public static final String hasShownRateDialog = "hasShownRateDialog";
    public static final String firstVPN = "firstVPN";
    public static final String firstTracker = "firstTracker";
    public static final String firstPatriot = "firstPatriot";
    public static final String firstThreat = "firstThreat";
    public static final String firstAirtight = "firstAirtight";
    public static final String firstOpenDate = "firstOpenDate";
    public static final String firstPopup = "firstPopup";
    public static final String appOpenCount = "appOpenCount";
    public static final String canShowRateDialog = "canShowRateDialog";
    public static final String showTrackerDialog = "showTrackerDialog";
    public static final String seenEula = "seenEula";
    public static final String seenRYTutorial = "seenRYTutorial";
    public static final String seenADPermOverlay = "seenADPermOverlay";
    public static final String overrideAndShowAD = "overrideAndShowAD";

    public static final String hasSSFirstScanOccurred = "hasSSFirstScanOccurred";
  }
}
