package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

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

  public ErrorResponse getErrorResponse() {
    return errorResponse;
  }

  @Expose
  @SerializedName("error")
  public ErrorResponse errorResponse;

  @Expose
  @SerializedName("result")
  private Result result;

  @Expose
  @SerializedName("jsonrpc")
  private String jsonrpc;

  public class Result {
    @Expose
    @SerializedName("is_tutorial_viewed")
    private boolean is_tutorial_viewed;

    public String getToken() {
      return token;
    }

    @Expose
    @SerializedName("token")
    private String token;

    public String getUniqueId() {
      return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
      this.uniqueId = uniqueId;
    }

    @Expose
    @SerializedName("unique_id")
    private String uniqueId;
  }
}
