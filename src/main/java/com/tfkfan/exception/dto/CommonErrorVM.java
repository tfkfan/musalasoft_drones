package com.tfkfan.exception.dto;

public class CommonErrorVM implements ErrorVM {

    private String code;
    private String message;

    public CommonErrorVM() {}

    public CommonErrorVM(String message, String code) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
