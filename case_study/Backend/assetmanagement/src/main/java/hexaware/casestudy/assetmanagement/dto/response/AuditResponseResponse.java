package hexaware.casestudy.assetmanagement.dto.response;

import hexaware.casestudy.assetmanagement.enums.AuditStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditResponseResponse {

    private Long auditResponseId;
    private Long auditRequestId;
    private String employeeName;
    private LocalDate responseDate;
    private AuditStatus verificationStatus;
    private String comment;
}