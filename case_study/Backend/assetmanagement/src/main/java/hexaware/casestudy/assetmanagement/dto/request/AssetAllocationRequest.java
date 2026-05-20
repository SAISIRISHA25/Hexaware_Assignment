package hexaware.casestudy.assetmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssetAllocationRequest {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Asset ID is required")
    private Long assetId;

    @FutureOrPresent(message = "Return date must be today or in the future")
    private LocalDate expectedReturnDate;

    // allocatedByAdminId removed — admin identity is extracted from JWT token in the controller.

    @Size(max = 255, message = "Remarks should be less than 255 characters")
    private String remarks;
}