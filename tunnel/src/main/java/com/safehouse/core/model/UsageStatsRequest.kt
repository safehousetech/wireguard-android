package com.safehouse.core.model

import com.google.gson.annotations.SerializedName

data class UsageStatsRequest (
    @SerializedName("eventType")
    val eventType : String,

    @SerializedName("increment")
    val increment : Long? = null
) {
    companion object {
        const val URLS_SCANNED = "urls_scanned"
        const val URLS_BLOCKED = "urls_blocked"
        const val DOMAINS_BLOCKED = "domains_blocked"
        const val VPN_USED = "vpn_used"
        const val AIRTIGHT_USED = "air_tight_used"
    }
}