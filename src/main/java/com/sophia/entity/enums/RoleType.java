package com.sophia.entity.enums;

public enum RoleType {

    ADMIN("Admin"),
    USER("User");

    public final String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
