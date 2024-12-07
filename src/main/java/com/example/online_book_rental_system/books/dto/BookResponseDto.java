package com.example.online_book_rental_system.books.dto;

import lombok.Data;

@Data
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Boolean availability;
    private String createdAt;
    private String updatedAt;
}
