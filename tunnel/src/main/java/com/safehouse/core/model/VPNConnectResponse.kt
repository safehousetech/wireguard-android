package com.safehouse.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class VPNConnectResponse {
    @Expose
    @SerializedName("peerConfig")
    val peerConfig: PeerConfig? = null
    @Expose
    @SerializedName("region")
    val region: String? = null
    @Expose
    @SerializedName("displayName")
    val displayName: String? = null

    var privateKey: String = ""
    var timeStamp: Long = 0
    var countryName: String = ""
    var publicKeyRequest = ""

    class PeerConfig {
        @Expose
        @SerializedName("interface")
        val interfaceResponse: InterfaceResponse? = null

        @Expose
        @SerializedName("peers")
        val peersList: ArrayList<Peers>? = null

        class InterfaceResponse {
            @Expose
            @SerializedName("address")
            val addressList: MutableList<String>? = null

            @Expose
            @SerializedName("dns")
            val dnsList: MutableList<String>? = null

            override fun toString(): String {
                return "InterfaceResponse(addressList=$addressList, dnsList=$dnsList)"
            }
        }

        class Peers {
            @Expose
            @SerializedName("publicKey")
            val publicKey: String? = null

            @Expose
            @SerializedName("hasPresharedKey")
            val hasPresharedKey: Boolean? = null

            @Expose
            @SerializedName("persistentKeepAlive")
            val persistentKeepAlive: String? = null

            @Expose
            @SerializedName("endpoint")
            val endPoint: String? = null

            @Expose
            @SerializedName("allowedIPs")
            val allowedIPs: MutableList<String>? = null

            override fun toString(): String {
                return "Peers(publicKey=$publicKey, hasPresharedKey=$hasPresharedKey, persistentKeepAlive=$persistentKeepAlive, endPoint=$endPoint, allowedIPs=$allowedIPs)"
            }
        }

        override fun toString(): String {
            return "PeerConfig(interfaceResponse=$interfaceResponse, peersList=$peersList)"
        }
    }

    override fun toString(): String {
        return "VPNConnectResponse(peerConfig=$peerConfig)"
    }
}