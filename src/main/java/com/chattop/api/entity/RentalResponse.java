package com.chattop.api.entity;

import lombok.Data;

@Data
public class RentalResponse {
    private String message;

    public RentalResponse(String message) {
        this.message = message;
    }
}
