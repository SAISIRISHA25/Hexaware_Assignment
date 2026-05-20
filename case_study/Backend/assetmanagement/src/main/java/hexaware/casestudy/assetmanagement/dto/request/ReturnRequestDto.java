package hexaware.casestudy.assetmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReturnRequestDto {

    // employeeId removed — extracted from the JWT token in the controller.

    @NotNull(message = "Asset ID is required")
    private Long assetId;

    @NotNull(message = "Allocation ID is required")
    private Long allocationId;

    @NotBlank(message = "Return reason is required")
    @Size(max = 255, message = "Return reason must be less than 255 characters")
    private String returnReason;
}