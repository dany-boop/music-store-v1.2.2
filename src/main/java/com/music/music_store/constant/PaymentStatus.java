package com.music.music_store.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    SETTLEMENT("settlement"),
    PENDING("pending"),
    DENY("deny"),
    CANCEL("cancel"),
    EXPIRE("expire");

    private final String name;

    public static PaymentStatus getByName(String name) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getName().equalsIgnoreCase(name))
                return paymentStatus;
        }
        return null;
    }
}