package com.renteasy.controller;

import com.renteasy.dto.LoginRequest;
import com.renteasy.dto.LoginResponse;
import com.renteasy.dto.UserDTO;
import com.renteasy.dto.UserRegistrationRequest;
import com.renteasy.entity.UserEntity;
import com.renteasy.security.JwtUtil;
import com.renteasy.service.UserService;

import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest registrationRequest) {
        try {
            // Map registration DTO to UserDTO
            UserDTO userDTO = modelMapper.map(registrationRequest, UserDTO.class);

            UserDTO createdUser = userService.registerUser(userDTO);

            // Return created user info without password
            createdUser.setPassword(null);

            return ResponseEntity.ok(createdUser);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate user by email + password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Fetch user details from DB
            UserDTO userDTO = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

            // Generate JWT token for this user
            String token = jwtUtil.generateJwtToken(loginRequest.getEmail());

            // Create response with token and user info (without password)
            userDTO.setPassword(null);
            LoginResponse loginResponse = new LoginResponse(token, userDTO);

            return ResponseEntity.ok(loginResponse);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // Get current logged-in user info endpoint
    // Requires JWT auth, uses Principal
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        try {
            // Email is principal name (from JWT filter)
            UserDTO userDTO = userService.loginUser(principal.getName(), null);
            // Passing null password here - service can be tweaked to just fetch user by email
            userDTO.setPassword(null);
            return ResponseEntity.ok(userDTO);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(404).body("User not found");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    
    // (Optional) Logout endpoint - if session-based
    // For JWT, usually logout is done frontend by deleting token
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("User logged out successfully");
    }

}


