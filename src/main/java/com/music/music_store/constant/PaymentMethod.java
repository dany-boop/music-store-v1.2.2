package com.music.music_store.constant;

import lombok.*;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    CRYPTOCURRENCY("cryptocurrency"),
    CREDIT_CARD("credit_card"),
    MOBILE_PAYMENT("mobile_payment"),
    PAYPAL("paypal");

    private final String name;

    public static PaymentMethod getByName(String name) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.getName().equalsIgnoreCase(name))
                return paymentMethod;
        }
        return null;
    }
}
