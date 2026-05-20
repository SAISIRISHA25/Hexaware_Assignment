package hexaware.casestudy.assetmanagement.mapper;

import hexaware.casestudy.assetmanagement.dto.response.ServiceRequestResponse;
import hexaware.casestudy.assetmanagement.entity.ServiceRequest;

public class ServiceRequestMapper {

    public static ServiceRequestResponse toDto(ServiceRequest s) {
        return ServiceRequestResponse.builder()
                .serviceRequestId(s.getServiceRequestId())
                .employeeName(s.getEmployee().getFullName())
                .assetName(s.getAsset().getAssetName())
                .issueType(s.getIssueType())
                .description(s.getDescription())
                .requestDate(s.getRequestDate())
                .serviceStatus(s.getServiceStatus())
                .build();
    }
}