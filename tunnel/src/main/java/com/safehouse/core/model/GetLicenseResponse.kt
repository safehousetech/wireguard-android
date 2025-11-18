package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetLicenseResponse {
    @SerializedName("personal")
    @Expose
    var personal: LicenseTypeData = LicenseTypeData()

    @SerializedName("vip")
    @Expose
    var vip: LicenseTypeData = LicenseTypeData()

    @SerializedName("pro")
    @Expose
    var pro: LicenseTypeData = LicenseTypeData()

    @SerializedName("vip_plus")
    @Expose
    var vipPlus: LicenseTypeData = LicenseTypeData()

    class LicenseTypeData {
        @SerializedName("licenseType")
        var licenseType: String = ""

        @SerializedName("totalCount")
        var totalCount = 0

        @SerializedName("usedCount")
        var usedCount = 0

        @SerializedName("availableLicenses")
        var availableLicenses: List<License>? = null

        @SerializedName("usedLicenses")
        var usedLicenses: List<License>? = null
    }

    class License {
        @SerializedName("id")
        var id = 0

        @SerializedName("license_type")
        var licenseType: String? = null

        @SerializedName("license_length")
        var licenseLength: String? = null

        @SerializedName("expiry_date")
        var expiryDate: String? = null

        @SerializedName("license_hash")
        var licenseHash: String? = null

        @SerializedName("claimed_user_id")
        var claimedUserId: String? = null

        @SerializedName("product_type")
        var productType: String? = null

        @SerializedName("trail")
        var trail: String? = null

        @SerializedName("country_code")
        var country_code: String? = null

        @SerializedName("is_active")
        var isActive: Boolean = true

        @SerializedName("backend_insurance")
        var policies: List<Any> = listOf()
    }

    fun getTotalLicenses() : Int {
        return personal.totalCount + vip.totalCount + pro.totalCount + vipPlus.totalCount
    }

    fun getUsedLicenses() : Int {
        return personal.usedCount + vip.usedCount + pro.usedCount + vipPlus.usedCount
    }

    fun getAllAvailableLicences() : List<License> {
        val list : MutableList<License> = mutableListOf()
        list.addAll(personal.availableLicenses?: listOf())
        list.addAll(vip.availableLicenses?: listOf())
        list.addAll(pro.availableLicenses?: listOf())
        list.addAll(vipPlus.availableLicenses?: listOf())
        return list
    }

    fun getAllUsedLicences() : List<License> {
        val list : MutableList<License> = mutableListOf()
        list.addAll(personal.usedLicenses?: listOf())
        list.addAll(vip.usedLicenses?: listOf())
        list.addAll(pro.usedLicenses?: listOf())
        list.addAll(vipPlus.usedLicenses?: listOf())
        return list
    }

    fun getAllLicenses() : List<License> {
        val list : MutableList<License> = mutableListOf()
        list.addAll(getAllAvailableLicences())
        list.addAll(getAllUsedLicences())
        return list
    }
}