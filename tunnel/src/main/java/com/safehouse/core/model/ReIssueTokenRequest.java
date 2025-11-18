package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.inject.Inject;

public class ReIssueTokenRequest {
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

  @Inject
  public ReIssueTokenRequest(String id, Params params, String method, String jsonrpc) {
    this.id = id;
    this.params = params;
    this.method = method;
    this.jsonrpc = jsonrpc;
  }

  public static class Params {

    public Params(String deviceId, String otp) {
      this.deviceId = deviceId;
      this.otp = otp;
    }

    @Expose
    @SerializedName("deviceId")
    private String deviceId;

    @Expose
    @SerializedName("otp")
    private String otp;
  }
}
