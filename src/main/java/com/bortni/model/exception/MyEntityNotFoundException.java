package com.bortni.model.exception;

public class MyEntityNotFoundException extends RuntimeException {

    public MyEntityNotFoundException(String message) {
        super(message);
    }
}
