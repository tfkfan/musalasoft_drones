package com.tfkfan.exception.dto;

import java.util.Arrays;
import java.util.List;

public class ErrorsVM {

    private List<ErrorVM> errors;

    public ErrorsVM() {}

    public ErrorsVM(List<ErrorVM> errors) {
        this.errors = errors;
    }

    public ErrorsVM(ErrorVM... errors) {
        this.errors = Arrays.asList(errors);
    }

    public List<ErrorVM> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorVM> errors) {
        this.errors = errors;
    }
}
