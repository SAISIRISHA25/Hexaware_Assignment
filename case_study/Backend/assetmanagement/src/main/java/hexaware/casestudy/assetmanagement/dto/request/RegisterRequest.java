package hexaware.casestudy.assetmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must be less than 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    // FIX: Added @Pattern for password strength.
    // Original only enforced @Size(min=8) — a password like "aaaaaaaa" passed silently.
    // The spec explicitly requires "minimum length, special characters".
    // New rule: at least 8 chars, one uppercase letter, one digit, one special character.
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "Password must contain at least one uppercase letter, one digit, and one special character"
    )
    private String password;

    @Size(max = 100, message = "Department must be less than 100 characters")
    private String department;

    @Size(max = 100, message = "Designation must be less than 100 characters")
    private String designation;

    // Role is intentionally NOT exposed here.
    // All self-registrations are EMPLOYEE by default.
    // Admin registration uses the secured POST /api/v1/auth/register-admin endpoint.
}
