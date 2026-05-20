package hexaware.casestudy.assetmanagement.dto.response;

import hexaware.casestudy.assetmanagement.enums.AuditStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditRequestResponse {

    private Long auditRequestId;
    private String employeeName;
    private String assetName;
    private LocalDate auditDate;
    private AuditStatus auditStatus;
}