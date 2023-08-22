package com.tfkfan.exception;

public class DroneIsOverloadedException extends BusinessException {

    static ExceptionDictionary dict = ExceptionDictionary.DRONE_IS_OVERLOADED;

    public DroneIsOverloadedException() {
        super(dict);
    }

    public DroneIsOverloadedException(String message) {
        super(message, dict);
    }
}
