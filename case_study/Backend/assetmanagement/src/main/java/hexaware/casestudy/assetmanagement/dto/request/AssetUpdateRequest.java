package hexaware.casestudy.assetmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * FIX: Separate DTO for asset updates.
 *
 * Original code reused AssetCreateRequest for both create and update.
 * Problems with that approach:
 *  1. assetNo should NOT be changeable after creation (business rule).
 *  2. createdByUserId makes no sense on an update payload.
 *  3. Using the same DTO hides intent and makes Swagger docs confusing.
 *
 * assetNo is intentionally absent here — it cannot be changed via update.
 */
@Data
public class AssetUpdateRequest {

    @NotBlank(message = "Asset name is required")
    @Size(max = 100, message = "Asset name must be less than 100 characters")
    private String assetName;

    @Size(max = 100, message = "Asset model must be less than 100 characters")
    private String assetModel;

    @PastOrPresent(message = "Manufacturing date cannot be in the future")
    private LocalDate manufacturingDate;

    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

    /**
     * FIX: BigDecimal instead of Double — matches the entity and avoids
     * floating-point precision issues for financial values.
     */
    @NotNull(message = "Asset value is required")
    @Positive(message = "Asset value must be positive")
    private BigDecimal assetValue;

    @Size(max = 50, message = "Asset condition must be less than 50 characters")
    private String assetCondition;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Size(max = 500, message = "Image URL must be less than 500 characters")
    private String imageUrl;
}
