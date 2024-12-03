package com.example.online_book_rental_system.auth.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String email;
    private String name;
    private String roles;
}
