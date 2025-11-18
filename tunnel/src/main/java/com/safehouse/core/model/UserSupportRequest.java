package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSupportRequest {

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

  public UserSupportRequest(String id, Params params, String method, String jsonrpc) {
    this.id = id;
    this.params = params;
    this.method = method;
    this.jsonrpc = jsonrpc;
  }

  public static class Params {

    public Params(
        String name,
        String subject,
        String email,
        String mca,
        String phone,
        String phoneNumber,
        String issue_type,
        String issue_description,
        String app_ver,
        String os_ver,
        String brand,
        String model) {
      this.email = email;
      this.name = name;
      this.subject = subject;
      this.email = email;
      this.mca = mca;
      this.phone = phone;
      this.issue_type = issue_type;
      this.issue_description = issue_description;
      this.app_ver = app_ver;
      this.os_ver = os_ver;
      this.brand = brand;
      this.model = model;
      this.phoneNumber = phoneNumber;
    }

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("subject")
    private String subject;

    @Expose
    @SerializedName("mca")
    private String mca;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("phone_number")
    private String phoneNumber;

    @Expose
    @SerializedName("issue_type")
    private String issue_type;

    @Expose
    @SerializedName("issue_description")
    private String issue_description;

    @Expose
    @SerializedName("app_ver")
    private String app_ver;

    @Expose
    @SerializedName("os_ver")
    private String os_ver;

    @Expose
    @SerializedName("brand")
    private String brand;

    @Expose
    @SerializedName("model")
    private String model;
  }
}
