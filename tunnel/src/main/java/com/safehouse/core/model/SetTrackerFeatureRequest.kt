package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SetTrackerFeatureRequest (
    @Expose @SerializedName("parentalControl")
    var parentalControl: Boolean = false,

    @Expose @SerializedName("lock")
    var lock: Boolean,

    @Expose @SerializedName("siren")
    var siren: Boolean,

    @Expose @SerializedName("location")
    var location: Boolean,

    @Expose @SerializedName("snapshot")
    var snapshot: Boolean,
)