package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import javax.inject.Inject;

public class AttachLicenseRequest {
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

  @Inject
  public AttachLicenseRequest(
      String id, Params params, String method, String jsonrpc) {
    this.id = id;
    this.params = params;
    this.method = method;
    this.jsonrpc = jsonrpc;
  }

  public static class Params {

    public Params(ArrayList<String> licenses) {
      this.licenses = licenses;
    }

    @Expose
    @SerializedName("licenses")
    private ArrayList<String> licenses;
  }
}
