package com.example.online_book_rental_system.auth.service;

import com.example.online_book_rental_system.auth.model.User;
import com.example.online_book_rental_system.auth.repository.UserRepository;
import com.example.online_book_rental_system.auth.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String currentUserEmail = AuthUtil.getCurrentUserEmail();
        return userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
