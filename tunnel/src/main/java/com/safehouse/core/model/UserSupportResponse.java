package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSupportResponse {

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

    public String getData() {
      return data;
    }

    @Expose
    @SerializedName("data")
    private String data;
  }
}
