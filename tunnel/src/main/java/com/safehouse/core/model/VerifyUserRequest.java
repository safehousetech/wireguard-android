package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyUserRequest {

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

  public VerifyUserRequest(String id, Params params, String method, String jsonrpc) {
    this.id = id;
    this.params = params;
    this.method = method;
    this.jsonrpc = jsonrpc;
  }

  public static class Params {

    public Params(String email, String phone, String alternateEmail, String alternatePhone) {
      this.email = email;
      this.phone = phone;
      this.alternateEmail = alternateEmail;
      this.alternatePhone = alternatePhone;
    }

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("alternateEmail")
    private String alternateEmail;

    @Expose
    @SerializedName("alternatePhone")
    private String alternatePhone;
  }
}
