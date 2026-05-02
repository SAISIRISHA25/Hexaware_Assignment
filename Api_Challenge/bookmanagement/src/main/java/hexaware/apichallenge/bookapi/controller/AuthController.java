package hexaware.apichallenge.bookapi.controller;

import hexaware.apichallenge.bookapi.dto.ApiResponse;
import hexaware.apichallenge.bookapi.dto.AuthResponse;
import hexaware.apichallenge.bookapi.dto.LoginRequest;
import hexaware.apichallenge.bookapi.entity.User;
import hexaware.apichallenge.bookapi.exception.DuplicateResourceException;
import hexaware.apichallenge.bookapi.exception.InvalidCredentialsException;
import hexaware.apichallenge.bookapi.repository.UserRepository;
import hexaware.apichallenge.bookapi.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody LoginRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists: " + request.getUsername());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        201,
                        "User registered successfully",
                        null
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        200,
                        "Login successful",
                        new AuthResponse(token)
                )
        );
    }
}