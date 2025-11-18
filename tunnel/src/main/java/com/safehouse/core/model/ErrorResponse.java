package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
  public ErrorResult getResult() {
    return result;
  }

  @Expose
  @SerializedName("result")
  private ErrorResult result;

  public class ErrorResult {
    public String getMessage() {
      return message;
    }

    public String getErrorCode() {
      return errorCode;
    }

    public int getErrorId() {
      return errorId;
    }

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("errorid")
    private int errorId;

    @Expose
    @SerializedName("errorcode")
    private String errorCode;
  }
}
