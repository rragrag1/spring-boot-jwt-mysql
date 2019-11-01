package com.sushil.dangi.springbootjwtmysql.web;

import com.sushil.dangi.springbootjwtmysql.exception.InvalidTokenException;
import com.sushil.dangi.springbootjwtmysql.response.ResponseMessage;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SignatureException.class)
    public final ResponseEntity<?> handleProjectIdException(InvalidTokenException ex,
                                                            WebRequest webRequest) {
        ResponseMessage response = new ResponseMessage("Failed", ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
