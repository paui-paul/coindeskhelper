package com.cathay.bank.coindeskhelper.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
import com.cathay.bank.coindeskhelper.vos.RestResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<RestResult<Object>> handler(Exception exception) {
        RestResult<Object> result = new RestResult<>();
        result.setSuccess(false);
        result.setMessage(exception.getMessage());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<RestResult<Map<String, String>>> handler(
            MethodArgumentNotValidException exception) {
        RestResult<Map<String, String>> result = new RestResult<>();
        result.setSuccess(false);
        result.setMessage("Validation failed for argument");
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        result.setResult(errors);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BitcoinException.class})
    public ResponseEntity<RestResult<Object>> handler(BitcoinException exception) {
        RestResult<Object> result = new RestResult<>();
        result.setSuccess(false);
        result.setMessage(exception.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
