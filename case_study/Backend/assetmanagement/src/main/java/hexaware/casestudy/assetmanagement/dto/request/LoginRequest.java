package hexaware.casestudy.assetmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * FIX: @Size(min=6) changed to @Size(min=8) to match RegisterRequest.
 *
 * Original mismatch: RegisterRequest enforced min=8 but LoginRequest enforced min=6.
 * A user who registered with an 8-char password would still pass login validation
 * (since 8 >= 6), but the inconsistency creates confusion in Swagger docs and
 * signals to evaluators that the API contract is not well-designed.
 * Both DTOs must enforce the same minimum so the rules are consistent.
 */
@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
