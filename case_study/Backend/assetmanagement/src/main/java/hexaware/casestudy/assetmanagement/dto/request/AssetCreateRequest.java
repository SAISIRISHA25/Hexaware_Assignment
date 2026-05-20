package hexaware.casestudy.assetmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Used ONLY for asset creation (POST).
 * For updates (PUT/PATCH) use {@link AssetUpdateRequest}.
 *
 * FIX: createdByUserId removed — admin identity is now extracted from the
 * JWT principal inside the service layer (SecurityUtil.getCurrentUser()).
 * Accepting it from the request body is a security issue: any caller could
 * claim to be a different admin.
 */
@Data
public class AssetCreateRequest {

    @NotBlank(message = "Asset number is required")
    private String assetNo;

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
     * FIX: BigDecimal instead of Double.
     * Matches the entity column (DECIMAL 10,2) and avoids floating-point precision loss.
     */
    @NotNull(message = "Asset value is required")
    @Positive(message = "Asset value must be positive")
    private BigDecimal assetValue;

    @Size(max = 50, message = "Asset condition must be less than 50 characters")
    private String assetCondition;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    // createdByUserId intentionally removed — resolved from JWT in service layer

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Size(max = 500, message = "Image URL must be less than 500 characters")
    private String imageUrl;
}
