package hexaware.casestudy.assetmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AuditRequestDto {

    // adminId removed — extracted from the JWT token in the controller.

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Asset ID is required")
    private Long assetId;

    @Size(max = 255, message = "Remarks must be less than 255 characters")
    private String remarks;
}