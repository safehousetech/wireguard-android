package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordRequest {
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

  public ResetPasswordRequest(String id, Params params, String method, String jsonrpc) {
    this.id = id;
    this.params = params;
    this.method = method;
    this.jsonrpc = jsonrpc;
  }

  public static class Params {

    public Params(String username) {
      this.username = username;
    }

    @Expose
    @SerializedName("username")
    private String username;
  }
}
