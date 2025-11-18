package com.safehouse.core.model;

import com.google.gson.annotations.SerializedName;

public class DeviceInfoModel {
  @SerializedName("model")
  private String model;

  @SerializedName("osVersion")
  private String osVersion;

  @SerializedName("osBuild")
  private String osBuild;

  @SerializedName("imeI1")
  private String imeI1;

  @SerializedName("imeI2")
  private String imeI2;

  @SerializedName("siM1")
  private String siM1;

  @SerializedName("siM2")
  private String siM2;

  @SerializedName("countryCode1")
  private String countryCode1;

  @SerializedName("mobileNo1")
  private String mobileNo1;

  @SerializedName("countryCode2")
  private String countryCode2;

  @SerializedName("mobileNo2")
  private String mobileNo2;

  @SerializedName("battery")
  private String battery;

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getOsVerson() {
    return osVersion;
  }

  public void setOsVerson(String osVerson) {
    this.osVersion = osVerson;
  }

  public String getOsBuild() {
    return osBuild;
  }

  public void setOsBuild(String osBuild) {
    this.osBuild = osBuild;
  }

  public String getImeI1() {
    return imeI1;
  }

  public void setImeI1(String imeI1) {
    this.imeI1 = imeI1;
  }

  public String getImeI2() {
    return imeI2;
  }

  public void setImeI2(String imeI2) {
    this.imeI2 = imeI2;
  }

  public String getSiM1() {
    return siM1;
  }

  public void setSiM1(String siM1) {
    this.siM1 = siM1;
  }

  public String getSiM2() {
    return siM2;
  }

  public void setSiM2(String siM2) {
    this.siM2 = siM2;
  }

  public String getCountryCode1() {
    return countryCode1;
  }

  public void setCountryCode1(String countryCode1) {
    this.countryCode1 = countryCode1;
  }

  public String getMobileNo1() {
    return mobileNo1;
  }

  public void setMobileNo1(String mobileNo1) {
    this.mobileNo1 = mobileNo1;
  }

  public String getCountryCode2() {
    return countryCode2;
  }

  public void setCountryCode2(String countryCode2) {
    this.countryCode2 = countryCode2;
  }

  public String getMobileNo2() {
    return mobileNo2;
  }

  public void setMobileNo2(String mobileNo2) {
    this.mobileNo2 = mobileNo2;
  }

  public String getBattery() {
    return battery;
  }

  public void setBattery(String battery) {
    this.battery = battery;
  }
}
