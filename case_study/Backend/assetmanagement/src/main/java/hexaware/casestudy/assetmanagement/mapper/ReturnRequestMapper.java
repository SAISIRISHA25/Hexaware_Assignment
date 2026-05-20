package hexaware.casestudy.assetmanagement.mapper;

import hexaware.casestudy.assetmanagement.dto.response.ReturnRequestResponse;
import hexaware.casestudy.assetmanagement.entity.ReturnRequest;

public class ReturnRequestMapper {

    public static ReturnRequestResponse toDto(ReturnRequest r) {
        return ReturnRequestResponse.builder()
                .returnRequestId(r.getReturnRequestId())
                .employeeName(r.getEmployee().getFullName())
                .assetName(r.getAsset().getAssetName())
                .requestDate(r.getRequestDate())
                .returnStatus(r.getReturnStatus())
                .build();
    }
}