package com.safehouse.core.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginModelClass {

  @SerializedName("user")
  public String user;

  @SerializedName("pass")
  public String pass;

  @SerializedName("device")
  public String device;

  @SerializedName("app")
  public String app;

  /*License*/
  @SerializedName("license_hash")
  private String license_hash;

  @SerializedName("expiry_date")
  private String expiry_date;

  @SerializedName("id")
  private int id;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  private String token;

  public String getLicense_hash() {
    return license_hash;
  }

  public void setLicense_hash(String license_hash) {
    this.license_hash = license_hash;
  }

  public String getExpiry_date() {
    return expiry_date;
  }

  public void setExpiry_date(String expiry_date) {
    this.expiry_date = expiry_date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
  }
}
