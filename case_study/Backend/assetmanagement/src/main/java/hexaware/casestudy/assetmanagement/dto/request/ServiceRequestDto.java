package hexaware.casestudy.assetmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ServiceRequestDto {

    // employeeId removed — extracted from the JWT token in the controller.

    @NotNull(message = "Asset ID is required")
    private Long assetId;

    @NotBlank(message = "Issue type is required")
    @Size(max = 100, message = "Issue type must be less than 100 characters")
    private String issueType;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;
}