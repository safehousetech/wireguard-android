package com.safehouse.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppReviewRequest {
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

  public AppReviewRequest(String id, Params params, String method, String jsonrpc) {
    this.id = id;
    this.params = params;
    this.method = method;
    this.jsonrpc = jsonrpc;
  }

  public static class Params {

    @Expose
    @SerializedName("feedback")
    private String feedback;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("stars")
    private int stars;

    public Params(String feedback, String date, int stars) {
      this.feedback = feedback;
      this.date = date;
      this.stars = stars;
    }
  }
}
