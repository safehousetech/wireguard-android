package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

  @Expose
  @SerializedName("id")
  private String id;

  @Expose
  @SerializedName("params")
  private Params params;

  @Expose
  @SerializedName("method")
  private String method;

  @Expose
  @SerializedName("jsonrpc")
  private String jsonrpc;

  public LoginRequest(String id, Params params, String method, String jsonrpc) {
    this.id = id;
    this.params = params;
    this.method = method;
    this.jsonrpc = jsonrpc;
  }

  public static class Params {
    @Expose
    @SerializedName("deviceId")
    private String deviceId;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("customer")
    private String customer;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("email")
    private String email;

    public Params(
        String deviceId,
        String password,
        String username,
        String customer,
        String phone,
        String email) {
      this.deviceId = deviceId;
      this.password = password;
      this.username = username;
      this.customer = customer;
      this.phone = phone;
      this.email = email;
    }
  }
}
