package com.tfkfan.exception.dto;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.FieldError;

public class ValidationErrorVM implements ErrorVM {

    private final List<FieldError> fieldErrors = new ArrayList<>();

    public ValidationErrorVM() {}

    public void addFieldError(String objectName, String path, String message) {
        FieldError error = new FieldError(objectName, path, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
