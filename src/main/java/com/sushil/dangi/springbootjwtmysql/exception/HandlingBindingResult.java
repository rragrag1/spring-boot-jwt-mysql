package com.sushil.dangi.springbootjwtmysql.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface HandlingBindingResult {
    ResponseEntity<?> collectErrorMessage(BindingResult result);
}
