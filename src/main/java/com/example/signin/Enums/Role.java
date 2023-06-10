package com.example.signin.Enums;

public enum Role {
    ADMIN(1),
    MANAGER(2),
    USER(3);

    private int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
