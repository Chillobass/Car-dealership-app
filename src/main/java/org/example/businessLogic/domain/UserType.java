package org.example.businessLogic.domain;

public enum UserType {
    LOCAL_MANAGER,
    LOCAL_ADMIN,
    CLIENT,
    SYS_ADMIN;

    public static UserType fromString(String value) {
        if (value == null) return null;

        for (UserType type : UserType.values()) {
            if (type.name().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("unknown user: " + value);
    }
}
