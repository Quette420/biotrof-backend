package ru.volgau.graduatework.biotrofbackend.dictionary;

public enum Permission {
    EMPLOYER_PERMISSIONS("EMPLOYER"),
    WAREHOUSE_MANAGER("WAREHOUSE_MANAGER"),

    ADMIN_PERMISSIONS("ADMIN");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}