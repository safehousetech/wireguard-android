package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordResponse {
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

    public void setData(String data) {
      this.data = data;
    }

    public String getLink() {
      return link;
    }

    public void setLink(String link) {
      this.link = link;
    }

    @Expose
    @SerializedName("data")
    private String data;

    @Expose
    @SerializedName("link")
    private String link;
  }
}
