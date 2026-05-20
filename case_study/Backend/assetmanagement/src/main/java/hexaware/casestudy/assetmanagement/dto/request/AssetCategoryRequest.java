package hexaware.casestudy.assetmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AssetCategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Category name must be less than 100 characters")
    private String categoryName;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;
}