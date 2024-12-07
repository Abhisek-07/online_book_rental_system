package com.example.online_book_rental_system.auth.dto;

import com.example.online_book_rental_system.auth.model.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String email;
    private String name;
    private String message;
    private String token;
    private long expiresIn;
}
