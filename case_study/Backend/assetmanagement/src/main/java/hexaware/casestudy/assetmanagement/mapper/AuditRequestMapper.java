package hexaware.casestudy.assetmanagement.mapper;

import hexaware.casestudy.assetmanagement.dto.response.AuditRequestResponse;
import hexaware.casestudy.assetmanagement.entity.AuditRequest;

public class AuditRequestMapper {

    public static AuditRequestResponse toDto(AuditRequest a) {
        return AuditRequestResponse.builder()
                .auditRequestId(a.getAuditRequestId())
                .employeeName(a.getEmployee().getFullName())
                .assetName(a.getAsset().getAssetName())
                .auditDate(a.getAuditDate())
                .auditStatus(a.getAuditStatus())
                .build();
    }
}