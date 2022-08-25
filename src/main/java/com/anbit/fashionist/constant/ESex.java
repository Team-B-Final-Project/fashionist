package com.anbit.fashionist.constant;

public enum ESex {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    private final String name;

    ESex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
