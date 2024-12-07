package com.example.online_book_rental_system.books.service;

import com.example.online_book_rental_system.books.dto.BookRequestDto;
import com.example.online_book_rental_system.books.dto.BookResponseDto;
import com.example.online_book_rental_system.books.model.Book;
import com.example.online_book_rental_system.books.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public BookResponseDto addBook(BookRequestDto bookRequestDTO) {
        Book book = modelMapper.map(bookRequestDTO, Book.class);
        book.setAvailability(true);
        Book savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookResponseDto.class);
    }

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookResponseDto.class))
                .collect(Collectors.toList());
    }

    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return modelMapper.map(book, BookResponseDto.class);
    }

    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setGenre(bookRequestDTO.getGenre());
        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookResponseDto.class);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }
}
