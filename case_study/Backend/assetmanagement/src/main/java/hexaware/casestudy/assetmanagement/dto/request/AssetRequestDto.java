package hexaware.casestudy.assetmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AssetRequestDto {

    // employeeId removed — extracted from the JWT token in the controller.

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotBlank(message = "Request reason is required")
    @Size(max = 255, message = "Request reason must be less than 255 characters")
    private String requestReason;
}