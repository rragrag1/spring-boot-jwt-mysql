package com.sushil.dangi.springbootjwtmysql.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseMessage {
    private String message;
    private String details;
}
