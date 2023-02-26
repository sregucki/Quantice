package com.quantice.authservice.model.enums;

public enum AuthProvider {
    QUANTICE, GOOGLE;

    public static AuthProvider valueOfIgnoreCase(String name) {

        return valueOf(name.toUpperCase());
    }
}
