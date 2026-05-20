package hexaware.casestudy.assetmanagement.mapper;

import hexaware.casestudy.assetmanagement.dto.response.AssetCategoryResponse;
import hexaware.casestudy.assetmanagement.entity.AssetCategory;

public class AssetCategoryMapper {

    public static AssetCategoryResponse toDto(AssetCategory category) {
        return AssetCategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .status(category.getStatus())
                .build();
    }
}