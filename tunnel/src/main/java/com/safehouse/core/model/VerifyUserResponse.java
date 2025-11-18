package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyUserResponse {
  @Expose
  @SerializedName("id")
  private String id;

  public String getId() {
    return id;
  }

  public String getJsonrpc() {
    return jsonrpc;
  }

  @Expose
  @SerializedName("jsonrpc")
  private String jsonrpc;

  public ErrorResponse getErrorResponse() {
    return errorResponse;
  }

  @Expose
  @SerializedName("error")
  public ErrorResponse errorResponse;

  public class Result {}
}
