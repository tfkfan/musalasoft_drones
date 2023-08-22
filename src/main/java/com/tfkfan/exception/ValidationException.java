package com.tfkfan.exception;

public class ValidationException extends BusinessException {

    static ExceptionDictionary dict = ExceptionDictionary.VALIDATION_ERROR;

    public ValidationException() {
        super(dict);
    }

    public ValidationException(String message) {
        super(message, dict);
    }
}
