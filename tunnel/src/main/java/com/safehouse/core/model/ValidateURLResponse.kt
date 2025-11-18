package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ValidateURLResponse {
  @Expose @SerializedName("id") var id: String? = null

  @Expose @SerializedName("result") var result: Result? = null

  @Expose @SerializedName("jsonrpc") var jsonrpc: String? = null

  class Result {
    @Expose @SerializedName("result") var result: String? = null
  }
}
