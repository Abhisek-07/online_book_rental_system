package com.example.online_book_rental_system.auth.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String message;
    private String token;
}
