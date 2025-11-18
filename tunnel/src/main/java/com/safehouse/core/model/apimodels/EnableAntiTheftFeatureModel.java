package com.safehouse.core.model.apimodels;

public class EnableAntiTheftFeatureModel {

  private boolean enableLocation;
  private boolean enableLock;
  private boolean enableSiren;
  private boolean enableSnapshot;
  private boolean enableParentalControl;

  public boolean isEnableLocation() {
    return enableLocation;
  }

  public void setEnableLocation(boolean enableLocation) {
    this.enableLocation = enableLocation;
  }

  public boolean isEnableLock() {
    return enableLock;
  }

  public void setEnableLock(boolean enableLock) {
    this.enableLock = enableLock;
  }

  public boolean isEnableSiren() {
    return enableSiren;
  }

  public void setEnableSiren(boolean enableSiren) {
    this.enableSiren = enableSiren;
  }

  public boolean isEnableSnapshot() {
    return enableSnapshot;
  }

  public void setEnableSnapshot(boolean enableSnapshot) {
    this.enableSnapshot = enableSnapshot;
  }

  public boolean isEnableParentalControl() {
    return enableParentalControl;
  }

  public void setEnableParentalControl(boolean enableParentalControl) {
    this.enableParentalControl = enableParentalControl;
  }
}
