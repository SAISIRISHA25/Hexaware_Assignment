package hexaware.casestudy.assetmanagement.mapper;

import hexaware.casestudy.assetmanagement.dto.response.AuditResponseResponse;
import hexaware.casestudy.assetmanagement.entity.AuditResponse;

public class AuditResponseMapper {

    public static AuditResponseResponse toDto(AuditResponse a) {
        return AuditResponseResponse.builder()
                .auditResponseId(a.getAuditResponseId())
                .auditRequestId(a.getAuditRequest().getAuditRequestId())
                .employeeName(a.getEmployee().getFullName())
                .responseDate(a.getResponseDate())
                .verificationStatus(a.getVerificationStatus())
                .comment(a.getComment())
                .build();
    }
}