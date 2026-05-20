package hexaware.casestudy.assetmanagement.mapper;

import hexaware.casestudy.assetmanagement.dto.response.AssetAllocationResponse;
import hexaware.casestudy.assetmanagement.entity.AssetAllocation;

public class AssetAllocationMapper {

    public static AssetAllocationResponse toDto(AssetAllocation a) {
        return AssetAllocationResponse.builder()
        .allocationId(a.getAllocationId())
        .assetId(a.getAsset().getAssetId())
        .employeeName(a.getEmployee().getFullName())
        .assetName(a.getAsset().getAssetName())
                .allocatedDate(a.getAllocatedDate())
                .expectedReturnDate(a.getExpectedReturnDate())
                .actualReturnDate(a.getActualReturnDate())
                .allocationStatus(a.getAllocationStatus())
                .build();
    }
}