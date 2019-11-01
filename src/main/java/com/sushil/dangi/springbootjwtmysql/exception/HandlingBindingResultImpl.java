package com.sushil.dangi.springbootjwtmysql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HandlingBindingResultImpl implements HandlingBindingResult {

    @Override
    public ResponseEntity<?> collectErrorMessage(BindingResult result) {
        Map<String, String> errorMap = result
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,
                        FieldError::getDefaultMessage, (a, b) -> b));
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
