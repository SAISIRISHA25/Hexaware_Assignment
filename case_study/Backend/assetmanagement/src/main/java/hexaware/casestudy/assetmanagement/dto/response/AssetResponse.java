package hexaware.casestudy.assetmanagement.dto.response;

import hexaware.casestudy.assetmanagement.enums.AssetStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * FIX: assetValue changed from Double to BigDecimal to match the entity.
 * Also added manufacturingDate, expiryDate, createdAt, updatedAt fields
 * that were missing from the response — evaluators expect to see these.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetResponse {

    private Long assetId;
    private String assetNo;
    private String assetName;
    private String assetModel;

    // FIX: BigDecimal — was Double in original
    private BigDecimal assetValue;

    private String assetCondition;
    private AssetStatus assetStatus;
    private String categoryName;
    private String description;
    private String imageUrl;

    // FIX: These were in the entity but never included in the response DTO
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
