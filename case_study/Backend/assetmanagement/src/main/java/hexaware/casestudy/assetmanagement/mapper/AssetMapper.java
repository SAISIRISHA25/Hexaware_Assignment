package hexaware.casestudy.assetmanagement.mapper;

import hexaware.casestudy.assetmanagement.dto.response.AssetResponse;
import hexaware.casestudy.assetmanagement.entity.Asset;

public class AssetMapper {

    public static AssetResponse toDto(Asset asset) {
        return AssetResponse.builder()
                .assetId(asset.getAssetId())
                .assetNo(asset.getAssetNo())
                .assetName(asset.getAssetName())
                .assetModel(asset.getAssetModel())
                // FIX: BigDecimal — was Double in original
                .assetValue(asset.getAssetValue())
                .assetCondition(asset.getAssetCondition())
                .assetStatus(asset.getAssetStatus())
                .categoryName(asset.getCategory().getCategoryName())
                .description(asset.getDescription())
                .imageUrl(asset.getImageUrl())
                // FIX: Include dates that were missing from original mapper
                .manufacturingDate(asset.getManufacturingDate())
                .expiryDate(asset.getExpiryDate())
                .createdAt(asset.getCreatedAt())
                .updatedAt(asset.getUpdatedAt())
                .build();
    }
}
