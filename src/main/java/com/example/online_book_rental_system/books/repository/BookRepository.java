package com.example.online_book_rental_system.books.repository;

import com.example.online_book_rental_system.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
