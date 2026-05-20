package hexaware.casestudy.assetmanagement.controller;

import hexaware.casestudy.assetmanagement.dto.response.ApiResponse;
import hexaware.casestudy.assetmanagement.dto.response.UserResponse;
import hexaware.casestudy.assetmanagement.mapper.UserMapper;
import hexaware.casestudy.assetmanagement.security.SecurityUtil;
import hexaware.casestudy.assetmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Management", description = "Admin operations for managing employee accounts. Employees can view their own profile via GET /me.")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecurityUtil securityUtil;

    @Operation(summary = "Get my profile",
            description = "Returns the profile of the currently authenticated user (resolved from JWT). Available to all roles.")
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile() {
        UserResponse response = UserMapper.toDto(securityUtil.getCurrentUser());
        return ResponseEntity.ok(ApiResponse.success("Profile fetched successfully", response));
    }

    @Operation(summary = "Get all employees",
            description = "Returns all active (non-deleted) employee accounts. Admin only.")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(
                ApiResponse.success("Users fetched successfully", userService.getAllUsers()));
    }

    @Operation(summary = "Get employee by ID",
            description = "Returns a single employee account by ID. Admin only.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("User fetched successfully", userService.getUserById(id)));
    }

    @Operation(summary = "Delete employee",
            description = "Soft-deletes an employee account. Blocked if the employee has active asset allocations. Admin only.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }
}
