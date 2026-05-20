package hexaware.casestudy.assetmanagement.dto.response;

import hexaware.casestudy.assetmanagement.enums.CategoryStatus;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetCategoryResponse {

    private Long categoryId;
    private String categoryName;
    private String description;

    // FIX: String → CategoryStatus to match the entity change.
    // Jackson serializes enums as their .name() by default — clients still receive
    // "ACTIVE" or "INACTIVE" as a JSON string, so the API contract is unchanged.
    private CategoryStatus status;
}
