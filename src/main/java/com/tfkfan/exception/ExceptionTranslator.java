package com.tfkfan.exception;

import com.tfkfan.exception.dto.CommonErrorVM;
import com.tfkfan.exception.dto.ErrorVM;
import com.tfkfan.exception.dto.ErrorsVM;
import com.tfkfan.exception.dto.ValidationErrorVM;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.postgresql.util.PSQLException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        logger.error(ex);
        return ResponseEntity.status(ex.getStatus()).body(new ErrorsVM(createErrors(ex)));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleSQLException(DataIntegrityViolationException ex) {
        Throwable root = ExceptionUtils.getRootCause(ex);
        logger.error(root);
        List<ErrorVM> errors = new ArrayList<>();

        if (root instanceof PSQLException && ((PSQLException) root).getSQLState().equals(ExceptionConstants.ALREADY_EXISTS_STATE)) {
            errors.add(
                new CommonErrorVM(
                    ExceptionConstants.formatConstraintMessage(ex.getMessage()),
                    ExceptionDictionary.ENTITY_ALREADY_EXISTS.getCode()
                )
            );
        } else {
            errors.addAll(createErrors(ex));
        }

        return ResponseEntity.status(ExceptionDictionary.INTERNAL_SERVER_ERROR.getStatus()).body(new ErrorsVM(errors));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ValidationErrorVM errors = new ValidationErrorVM(
            ExceptionDictionary.VALIDATION_ERROR.getDefaultMessage(),
            ExceptionDictionary.VALIDATION_ERROR.getCode()
        );
        for (FieldError fieldError : fieldErrors) {
            errors.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(ExceptionDictionary.VALIDATION_ERROR.getStatus()).body(new ErrorsVM(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception ex) {
        logger.error(ex);
        return ResponseEntity.status(ExceptionDictionary.INTERNAL_SERVER_ERROR.getStatus()).body(new ErrorsVM(createErrors(ex)));
    }

    private List<ErrorVM> createErrors(Exception ex) {
        List<ErrorVM> errors = new ArrayList<>();
        errors.add(
            new CommonErrorVM(
                ex.getMessage(),
                (ex instanceof BusinessException) ? ((BusinessException) ex).getCode() : ExceptionDictionary.INTERNAL_SERVER_ERROR.getCode()
            )
        );
        errors.addAll(
            Arrays
                .stream(ex.getSuppressed())
                .map(th ->
                    new CommonErrorVM(
                        th.getMessage(),
                        (ex instanceof BusinessException)
                            ? ((BusinessException) th).getCode()
                            : ExceptionDictionary.INTERNAL_SERVER_ERROR.getCode()
                    )
                )
                .toList()
        );
        return errors;
    }
}
