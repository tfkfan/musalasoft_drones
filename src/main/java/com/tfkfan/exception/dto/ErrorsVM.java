package com.tfkfan.exception.dto;

import java.util.Arrays;
import java.util.List;

public class ErrorsVM {

    private String message;
    private String code;
    private List<ErrorVM> errors;

    public ErrorsVM() {}

    public ErrorsVM(String message, String code, List<ErrorVM> errors) {
        this.errors = errors;
        this.message = message;
        this.code = code;
    }

    public ErrorsVM(String message, String code, ErrorVM... errors) {
        this.errors = Arrays.asList(errors);
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ErrorVM> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorVM> errors) {
        this.errors = errors;
    }
}
