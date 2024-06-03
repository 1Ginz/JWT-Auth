package com.example.jwt.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND( 404,"User not found"),
    ROLE_NOT_FOUND(404,"Role not found"),
    TOKEN_ERROR(404,"Expired or invalid JWT token"),
    LOGIN_ERROR(404,"Invalid username/password");
    private final int code;
    private final String message;
}

