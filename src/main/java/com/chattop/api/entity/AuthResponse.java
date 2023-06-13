package com.chattop.api.entity;

import lombok.Data;

@Data
public class AuthResponse {
    private String email;
    private String token;

    public AuthResponse() { }

    public AuthResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }

}
