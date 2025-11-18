package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VPNErrorResponse(
    @Expose
    @SerializedName("message")
    val message: Any?, // Can be List or String of error messages
    @Expose
    @SerializedName("error")
    val error: String?, // Error type
    @Expose
    @SerializedName("statusCode")
    val statusCode: Int?, // HTTP status code
    @Expose
    @SerializedName("timestamp")
    val timestamp: String? // Timestamp of the error
){
    // Convert message to a readable string
    fun getFormattedMessage(): String {
        return when (message) {
            is List<*> -> (message as List<String>).joinToString(", ") // Join array messages
            is String -> message // Return single message
            else -> "Unknown error"
        }
    }
}
