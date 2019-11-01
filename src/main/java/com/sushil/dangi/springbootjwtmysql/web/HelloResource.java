package com.sushil.dangi.springbootjwtmysql.web;

import com.sushil.dangi.springbootjwtmysql.dto.AuthenticationRequest;
import com.sushil.dangi.springbootjwtmysql.exception.HandlingBindingResult;
import com.sushil.dangi.springbootjwtmysql.response.AuthenticationResponse;
import com.sushil.dangi.springbootjwtmysql.response.ResponseMessage;
import com.sushil.dangi.springbootjwtmysql.utils.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HelloResource {

    private final HandlingBindingResult handlingBindingResult;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    public HelloResource(HandlingBindingResult handlingBindingResult,
                         AuthenticationManager authenticationManager,
                         UserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.handlingBindingResult = handlingBindingResult;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping({"hello", "hello/"})
    public String hello() {
        return "Hello World";
    }

    @PostMapping({"/authenticate", "/authenticate/"})
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody
                                                               AuthenticationRequest authenticationRequest,
                                                       BindingResult result) {

        ResponseEntity<?> responseEntity;

        if (result.hasErrors()) {
            responseEntity = handlingBindingResult.collectErrorMessage(result);
        } else {
            try {
                Authentication authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticationRequest.getUsername(),
                                authenticationRequest.getPassword()
                        )
                );
                if (authenticate.isAuthenticated()) {
                    final UserDetails userDetails = userDetailsService
                            .loadUserByUsername(authenticationRequest.getUsername());
                    String jwt = jwtUtil.generateToken(userDetails);
                    responseEntity = new ResponseEntity<>(
                            new AuthenticationResponse(jwt),
                            HttpStatus.OK
                    );
                } else {
                    responseEntity = new ResponseEntity<>(new ResponseMessage(
                            "Failed",
                            "Incorrect username or password"),
                            HttpStatus.BAD_REQUEST);
                }
            } catch (DisabledException |
                    AccountExpiredException |
                    LockedException |
                    BadCredentialsException |
                    CredentialsExpiredException e) {
                responseEntity = new ResponseEntity<>(new ResponseMessage(
                        "Failed",
                        "" + e.getLocalizedMessage()
                ), HttpStatus.BAD_REQUEST);
            }

        }
        return responseEntity;
    }
}
