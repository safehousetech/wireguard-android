package com.safehouse.core.model;

import com.google.gson.annotations.Expose;

public class ValidateRequest {

  @Expose private String id;
  @Expose private String jsonrpc;
  @Expose private String method;
  @Expose private Params params;
  @Expose private String fcm_token;

  public ValidateRequest(String id, Params params, String method, String jsonrpc) {
    this.id = id;
    this.params = params;
    this.method = method;
    this.jsonrpc = jsonrpc;
    this.fcm_token = params.fcm_token;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setJsonrpc(String jsonrpc) {
    this.jsonrpc = jsonrpc;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setParams(Params params) {
    this.params = params;
  }

  public static class Params {

    public Params(String deviceId, String fcm_token) {
      this.deviceId = deviceId;
      this.fcm_token = fcm_token;
    }

    @Expose private String deviceId;

    @Expose private String fcm_token;

    public void setDeviceId(String deviceId) {
      this.deviceId = deviceId;
    }

    public void setFcmToken(String fcmToken) {
      this.fcm_token = fcmToken;
    }
  }
}
