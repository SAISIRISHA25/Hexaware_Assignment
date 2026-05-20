package hexaware.casestudy.assetmanagement.controller;

import hexaware.casestudy.assetmanagement.dto.request.LoginRequest;
import hexaware.casestudy.assetmanagement.dto.request.RegisterRequest;
import hexaware.casestudy.assetmanagement.dto.response.ApiResponse;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.security.LoginRateLimiterService;
import hexaware.casestudy.assetmanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Register, login, and logout. Login is rate-limited to 5 attempts per minute per IP.")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final LoginRateLimiterService rateLimiter;

    @Operation(summary = "Register employee account",
            description = "Public endpoint. Creates a new ROLE_EMPLOYEE account.")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", authService.register(request)));
    }

   
    @Operation(summary = "Register admin account",
            description = "Creates a new ROLE_ADMIN account. Requires an existing admin JWT. Admin only.")
    @PostMapping("/register-admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Admin registered successfully", authService.registerAdmin(request)));
    }

    @Operation(summary = "Login",
            description = "Returns a JWT token on success. Rate-limited to 5 attempts per minute per IP.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        String clientIp = getClientIp(httpRequest);

        if (!rateLimiter.isAllowed(clientIp)) {
            long cooldown = rateLimiter.getCooldownSeconds(clientIp);
            throw new BadRequestException(
                    "Too many login attempts. Please try again in " + cooldown + " seconds.");
        }

        return ResponseEntity.ok(ApiResponse.success("Login successful", authService.login(request)));
    }

    @Operation(summary = "Logout",
            description = "Authenticated endpoint. Client must discard the JWT token. " +
                    "Server-side blacklisting requires Redis — not implemented in this version.")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout() {
        return ResponseEntity.ok(ApiResponse.success(
                "Logged out successfully. Please remove the token from client storage.", null));
    }

    // ── private helper ──────────────────────────────────────────────────────────

    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();  // first IP in chain is the real client
        }
        return request.getRemoteAddr();
    }
}
