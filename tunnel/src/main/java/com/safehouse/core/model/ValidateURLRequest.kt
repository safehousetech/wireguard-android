package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ValidateURLRequest(
  @Expose @SerializedName("id") private val id: String,
  @Expose @SerializedName("params") private val params: Params,
  @Expose @SerializedName("method") private val method: String,
  @Expose @SerializedName("jsonrpc") private val jsonrpc: String
) {
  data class Params(@Expose @SerializedName("url") val url: String)
}
