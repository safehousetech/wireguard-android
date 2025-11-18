package com.safehouse.core.vpn;

import androidx.annotation.Keep;

@Keep
public interface VPNConnectionListener {
    void onVPNConnected(Boolean isFreeVPN, String ipAddress, String region, String displayName);
    void onVPNDisconnected();
    void onVPNConnectionError(String errorMessage,int statusCode);
}