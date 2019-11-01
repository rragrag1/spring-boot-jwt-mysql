package com.sushil.dangi.springbootjwtmysql.response;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class AuthenticationResponse {
    private final String jwt;
}
