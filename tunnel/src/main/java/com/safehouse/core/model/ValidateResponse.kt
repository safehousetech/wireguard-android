package com.safehouse.core.model

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ValidateResponse {
    @Expose
    @SerializedName("id")
    private val id: String = ""

    @Expose
    @SerializedName("jsonrpc")
    private val jsonrpc: String = ""

    @SerializedName("status")
    @Expose
    var status: Boolean = false

    @SerializedName("message")
    @Expose
    var message: String = ""

    @SerializedName("statusCode")
    @Expose
    var statusCode = 0

    @SerializedName("timestamp")
    @Expose
    var timestamp: String = ""

    @SerializedName("user")
    var user: User = User()

    @SerializedName("license")
    var license: License = License()

    @SerializedName("error")
    var error: Error = Error()

    @SerializedName("showOnboardingCount")
    @Expose
    var showOnboardingCount: Int = 0

    class Error {
        @SerializedName("errorid")
        @Expose
        var errorid = 0

        @SerializedName("errorcode")
        @Expose
        var errorcode: String = ""

        @SerializedName("message")
        @Expose
        var message: String = ""
    }

    class License {
        @SerializedName("cyberInsurance")
        @Expose
        var cyberInsurance: Boolean = false
            get() = if (field != null) field else false

        @SerializedName("showInsuranceValidation")
        @Expose
        var showInsuranceValidation: Boolean = false

        @SerializedName("expiry")
        @Expose
        var expiry: String = ""

        @SerializedName("expiredDays")
        @Expose
        var expiredDays = 0

        @SerializedName("startDate")
        @Expose
        var startDate: String = ""

        @SerializedName("freeForEver")
        @Expose
        var freeForEver: Boolean = false

        @SerializedName("buyCategory")
        @Expose
        var buyCategory: String = ""

        @SerializedName("productType")
        @Expose
        var productType: String = ""
    }

    class User {
        @Expose
        @SerializedName("id")
        var id = 0

        @Expose
        @SerializedName("firstName")
        var firstName: String = ""

        @Expose
        @SerializedName("lastName")
        var lastName: String = ""

        @Expose
        @SerializedName("email")
        var email: String = ""

        @Expose
        @SerializedName("deviceId")
        var deviceId: String = ""

        @Expose
        @SerializedName("phone")
        var phone: String = ""

        @Expose
        @SerializedName("salutation")
        var salutation: String = ""

        @Expose
        @SerializedName("is_email_verified")
        var isEmailVerified: Boolean = false

        @Expose
        @SerializedName("is_phone_verified")
        var isPhoneVerified: Boolean = false

        @Expose
        @SerializedName("is_alternate_contact_added")
        var isAlternateContactAdded: Boolean = false

        @Expose
        @SerializedName("is_alternate_contact_verified")
        var isAlternateContactVerified: Boolean = false

        @Expose
        @SerializedName("alternateEmail")
        var alternateEmail: String = ""

        @Expose
        @SerializedName("alternatePhone")
        var alternatePhone: String = ""

        @Expose
        @SerializedName("is_alternate_email_verified")
        var isAlternateEmailVerified: Boolean = false

        @Expose
        @SerializedName("is_alternate_phone_verified")
        var isAlternatePhoneVerified: Boolean = false

        @Expose
        @SerializedName("uniqueId")
        var uniqueId: String = ""
    }

    companion object {
        @JvmStatic
        fun fromString(data: String?): ValidateResponse {
            val gson = Gson()
            return gson.fromJson(data, ValidateResponse::class.java)
        }
    }
}