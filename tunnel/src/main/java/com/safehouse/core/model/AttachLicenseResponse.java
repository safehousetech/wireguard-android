package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachLicenseResponse {
  @Expose
  @SerializedName("id")
  private String id;

  public String getId() {
    return id;
  }

  public Result getResult() {
    return result;
  }

  public String getJsonrpc() {
    return jsonrpc;
  }

  @Expose
  @SerializedName("result")
  private Result result;

  @Expose
  @SerializedName("jsonrpc")
  private String jsonrpc;

  public ErrorResponse getErrorResponse() {
    return errorResponse;
  }

  @Expose
  @SerializedName("error")
  public ErrorResponse errorResponse;

  public class Result {
    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("phone")
    private String phone;

    public String getMessage() {
      return message;
    }

    public String getEmail() {
      return email;
    }

    public String getPhone() {
      return phone;
    }
  }
}
