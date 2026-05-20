package hexaware.casestudy.assetmanagement.serviceimpl;

import hexaware.casestudy.assetmanagement.dto.request.LoginRequest;
import hexaware.casestudy.assetmanagement.dto.request.RegisterRequest;
import hexaware.casestudy.assetmanagement.dto.response.AuthResponse;
import hexaware.casestudy.assetmanagement.dto.response.UserResponse;
import hexaware.casestudy.assetmanagement.entity.User;
import hexaware.casestudy.assetmanagement.enums.Role;
import hexaware.casestudy.assetmanagement.enums.UserStatus;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.mapper.UserMapper;
import hexaware.casestudy.assetmanagement.repository.UserRepository;
import hexaware.casestudy.assetmanagement.security.JwtService;
import hexaware.casestudy.assetmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        return createUser(request, Role.ROLE_EMPLOYEE);
    }

    // FIX: New method — creates a ROLE_ADMIN account.
    // Called from POST /api/v1/auth/register-admin which is secured to existing ROLE_ADMIN users.
    // A default admin is also seeded on startup by DataLoader so the first admin always exists.
    @Override
    @Transactional
    public UserResponse registerAdmin(RegisterRequest request) {
        return createUser(request, Role.ROLE_ADMIN);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt received");

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed - email not found");
                    // Generic message intentional: don't reveal whether email exists
                    return new BadRequestException("Invalid email or password");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Login failed - incorrect password for user id: {}", user.getUserId());
            throw new BadRequestException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);
        log.info("Login successful for user id: {}", user.getUserId());

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    // ── private helper ──────────────────────────────────────────────────────────

    private UserResponse createUser(RegisterRequest request, Role role) {
        log.info("Registering new {} account", role.name());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration attempt with already-registered email");
            throw new BadRequestException("Email is already registered");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .role(role)
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);
        log.info("{} registered successfully with id: {}", role.name(), savedUser.getUserId());
        return UserMapper.toDto(savedUser);
    }
}
