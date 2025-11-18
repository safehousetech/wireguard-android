package com.safehouse.core.vpn;

import androidx.annotation.Keep;
import com.safehouse.core.model.PaymentResponse;

@Keep
public interface PaymentListener {
    void onPaymentSuccess(PaymentResponse paymentResponse);
    void onPaymentFailure(String errorMessage);
}
