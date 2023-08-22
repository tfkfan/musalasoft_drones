package com.tfkfan.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private String code;
    private HttpStatus status;

    public BusinessException(ExceptionDictionary dict) {
        super(dict.getDefaultMessage());
        this.code = dict.getCode();
        this.status = dict.getStatus();
    }

    public BusinessException(String message, ExceptionDictionary dict) {
        super(message);
        this.code = dict.getCode();
        this.status = dict.getStatus();
    }

    public BusinessException(String message, Throwable cause, ExceptionDictionary dict) {
        super(message, cause);
        this.code = dict.getCode();
        this.status = dict.getStatus();
    }

    public BusinessException(Throwable cause, ExceptionDictionary dict) {
        super(dict.getDefaultMessage(), cause);
        this.code = dict.getCode();
        this.status = dict.getStatus();
    }

    public BusinessException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace,
        ExceptionDictionary dict
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = dict.getCode();
        this.status = dict.getStatus();
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
