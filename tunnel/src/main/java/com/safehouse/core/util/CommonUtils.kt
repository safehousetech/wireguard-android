package com.safehouse.core.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.safehouse.core.model.VPNErrorResponse
import java.net.InetSocketAddress

object CommonUtils {
    fun extractIpAddress(endpoint: String): String {
        return try {
            val address = InetSocketAddress.createUnresolved(endpoint.substringBefore(":"), endpoint.substringAfter(":").toInt())
            address.hostString  // Returns only the IP part
        } catch (e: Exception) {
            e.localizedMessage.toString()
        }
    }

    fun parseErrorMessage(errorBody: String?): VPNErrorResponse? {
        return try {
            Gson().fromJson(errorBody, VPNErrorResponse::class.java)
        } catch (e: JsonSyntaxException) {
            null // Return null if parsing fails
        }
    }
}