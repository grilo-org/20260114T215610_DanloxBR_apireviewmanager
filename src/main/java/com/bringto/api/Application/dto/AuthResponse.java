package com.bringto.api.Application.config.dto;

public class AuthResponse {

    private String token;
    private String error;

    public AuthResponse() {}
    public AuthResponse(String token, String error) { this.token = token; this.error = error; }
}
