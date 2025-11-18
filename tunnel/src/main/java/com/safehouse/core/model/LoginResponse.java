package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

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
    @SerializedName("is_tutorial_viewed")
    private int is_tutorial_viewed;

    public String getToken() {
      return token;
    }

    @Expose
    @SerializedName("token")
    private String token;

    public int getId() {
      return id;
    }

    @Expose
    @SerializedName("id")
    private int id;

    public String getUsername() {
      return username;
    }

    public String getEmail() {
      return email;
    }

    private String username;

    private String email;

    @Expose
    @SerializedName("name")
    private String name;

    public String getPhone() {
      return phone;
    }

    @Expose
    @SerializedName("phone")
    private String phone;

    public String getName() {
      return name;
    }

    @Expose
    @SerializedName("is_email_verified")
    private Boolean isEmailVerified;

    public Boolean getEmailVerified() {
      return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
      isEmailVerified = emailVerified;
    }

    public Boolean getPhoneVerified() {
      return isPhoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
      isPhoneVerified = phoneVerified;
    }

    @Expose
    @SerializedName("is_phone_verified")
    private Boolean isPhoneVerified;

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
