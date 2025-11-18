package com.safehouse.core.model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator

class GetRegionResponse : java.io.Serializable {
    @SerializedName("regionCode")
    var regionCode: String = ""
    @SerializedName("regionName")
    var regionName: String = ""
    @SerializedName("distance")
    var distance: Double = 0.0
    @SerializedName("countryCode")
    var countryCode: String = ""
    @SerializedName("displayName")
    var displayName: String = ""
}