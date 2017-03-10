package com.intive.patronage.toz.exception;

public class NotFoundException extends RuntimeException {

    private final String name;

    public NotFoundException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
