package com.tfkfan.exception;

public class DroneIsBusyException extends BusinessException {

    static ExceptionDictionary dict = ExceptionDictionary.DRONE_IS_BUSY;

    public DroneIsBusyException() {
        super(dict);
    }

    public DroneIsBusyException(String message) {
        super(message, dict);
    }
}
