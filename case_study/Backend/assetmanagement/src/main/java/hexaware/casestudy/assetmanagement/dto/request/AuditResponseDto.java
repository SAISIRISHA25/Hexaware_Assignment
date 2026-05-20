package hexaware.casestudy.assetmanagement.dto.request;

import hexaware.casestudy.assetmanagement.enums.AuditStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * FIX: employeeId field removed.
 *
 * Original DTO included employeeId in the request body, allowing any employee
 * to claim they are a different employee when submitting an audit response.
 * The employee identity is now resolved server-side from the JWT token via
 * SecurityUtil.getCurrentUser() inside AuditResponseServiceImpl.
 */
@Data
public class AuditResponseDto {

    @NotNull(message = "Audit Request ID is required")
    private Long auditRequestId;

    // employeeId intentionally removed — resolved from JWT in service layer

    @NotNull(message = "Verification status is required")
    private AuditStatus verificationStatus;

    @Size(max = 255, message = "Comment must be less than 255 characters")
    private String comment;
}
