package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.safehouse.core.model.ErrorResponse

class UpdateUserResponse {
    @Expose
    @SerializedName("email")
    var email: String? = null
    @Expose
    @SerializedName("name")
    var name: String? = null
    @Expose
    @SerializedName("alternateEmail")
    var alternateEmail: String? = null
    @Expose
    @SerializedName("firstName")
    var firstName: String? = null
    @Expose
    @SerializedName("lastName")
    var lastName: String? = null
    @Expose
    @SerializedName("salutation")
    var salutation: String? = null
    @Expose
    @SerializedName("phone")
    var phone: String? = null
    @Expose
    @SerializedName("alternatePhone")
    var alternatePhone: String? = null
    @Expose
    @SerializedName("emailVerified")
    var emailVerified: Boolean? = null
    @Expose
    @SerializedName("phoneVerified")
    var phoneVerified: Boolean? = null
    @Expose
    @SerializedName("alternatePhoneVerified")
    var alternatePhoneVerified: Boolean? = null
    @Expose
    @SerializedName("alternateEmailVerified")
    var alternateEmailVerified: Boolean? = null
}