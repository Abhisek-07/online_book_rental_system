package com.example.online_book_rental_system.auth.controller;

import com.example.online_book_rental_system.auth.dto.LoginRequestDTO;
import com.example.online_book_rental_system.auth.dto.LoginResponseDTO;
import com.example.online_book_rental_system.auth.dto.RegisterUserDto;
import com.example.online_book_rental_system.auth.dto.UserResponseDTO;
import com.example.online_book_rental_system.auth.model.Role;
import com.example.online_book_rental_system.auth.model.User;
import com.example.online_book_rental_system.auth.service.AuthService;
import com.example.online_book_rental_system.auth.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;

    private final AuthService authenticationService;

    public AuthController(JwtService jwtService, AuthService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        // Generate JWT with the default role
        String jwtToken = jwtService.generateToken(registeredUser);

        // Create response with token
        UserResponseDTO response = new UserResponseDTO();
        response.setEmail(registeredUser.getEmail());
        response.setName(registeredUser.getName());
        response.setToken(jwtToken);
        response.setExpiresIn(jwtService.getExpirationTime());
        response.setMessage("User registered successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@Valid @RequestBody LoginRequestDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setMessage("User logged in successfully");
        return ResponseEntity.ok(loginResponse);
    }
}