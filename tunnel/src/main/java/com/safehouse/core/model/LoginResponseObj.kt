package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Login API Response object
 */
class LoginResponseObj {
    @Expose
    @SerializedName("phone")
    var phone: String? = null

    @Expose
    @SerializedName("email")
    var email: String? = null
}