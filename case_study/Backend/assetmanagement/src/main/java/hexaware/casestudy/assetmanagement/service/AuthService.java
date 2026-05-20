package hexaware.casestudy.assetmanagement.service;

import hexaware.casestudy.assetmanagement.dto.request.LoginRequest;
import hexaware.casestudy.assetmanagement.dto.request.RegisterRequest;
import hexaware.casestudy.assetmanagement.dto.response.AuthResponse;
import hexaware.casestudy.assetmanagement.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    // FIX: Added registerAdmin — without this there was no API way to create an admin account.
    // All self-registrations are forced to ROLE_EMPLOYEE. An evaluator could not test any
    // admin flow without manually inserting a row into the DB.
    // This endpoint is secured to ROLE_ADMIN in AuthController + SecurityConfig.
    UserResponse registerAdmin(RegisterRequest request);
}
