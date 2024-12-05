package com.example.online_book_rental_system.auth.controller;

import com.example.online_book_rental_system.auth.dto.LoginRequestDTO;
import com.example.online_book_rental_system.auth.dto.LoginResponseDTO;
import com.example.online_book_rental_system.auth.dto.UserRequestDTO;
import com.example.online_book_rental_system.auth.dto.UserResponseDTO;
import com.example.online_book_rental_system.auth.model.User;
import com.example.online_book_rental_system.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        if (userService.findUserByEmail(userRequestDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already in use!");
        }

        User user = User.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();

        userService.registerUser(user);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setEmail(user.getEmail());
        responseDTO.setName(user.getName());
        responseDTO.setRoles(user.getRoles());

        return ResponseEntity.ok(responseDTO);_
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        User user = userService.findUserByEmail(loginRequestDTO.getEmail());

        if (user == null || !userService.validatePassword(loginRequestDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password!");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user);

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setMessage("Login successful!");
        responseDTO.setToken(token);

        return ResponseEntity.ok(responseDTO);
    }
}
