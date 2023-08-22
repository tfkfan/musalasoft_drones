package com.tfkfan.exception;

public class DroneNotFoundException extends BusinessException {

    static ExceptionDictionary dict = ExceptionDictionary.DRONE_NOT_FOUND;

    public DroneNotFoundException() {
        super(dict);
    }

    public DroneNotFoundException(String message) {
        super(message, dict);
    }
}
