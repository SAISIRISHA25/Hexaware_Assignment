package hexaware.casestudy.assetmanagement.mapper;

import hexaware.casestudy.assetmanagement.dto.response.AssetRequestResponse;
import hexaware.casestudy.assetmanagement.entity.AssetRequest;

public class AssetRequestMapper {

    public static AssetRequestResponse toDto(AssetRequest req) {
        return AssetRequestResponse.builder()
                .requestId(req.getRequestId())
                .employeeName(req.getEmployee().getFullName())
                .categoryName(req.getCategory().getCategoryName())
                .requestReason(req.getRequestReason())
                .requestDate(req.getRequestDate())
                .requestStatus(req.getRequestStatus())
                .build();
    }
}