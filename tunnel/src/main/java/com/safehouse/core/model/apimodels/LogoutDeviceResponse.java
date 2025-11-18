package com.safehouse.core.model.apimodels;

public class LogoutDeviceResponse {

  private int deviceId;
  private String deviceName;
  private String brand;
  private String modelName;
  private String serialNo;
  private String imeI1;
  private String imeI2;
  private String fcmId;

  public int getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(int deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public String getSerialNo() {
    return serialNo;
  }

  public void setSerialNo(String serialNo) {
    this.serialNo = serialNo;
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

  public String getFcmId() {
    return fcmId;
  }

  public void setFcmId(String fcmId) {
    this.fcmId = fcmId;
  }

  public String getCommandType() {
    return commandType;
  }

  public void setCommandType(String commandType) {
    this.commandType = commandType;
  }

  private String commandType;
}
