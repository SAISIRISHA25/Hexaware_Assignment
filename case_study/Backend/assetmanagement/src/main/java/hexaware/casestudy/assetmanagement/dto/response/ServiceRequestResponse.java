package hexaware.casestudy.assetmanagement.dto.response;

import hexaware.casestudy.assetmanagement.enums.ServiceStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceRequestResponse {

    private Long serviceRequestId;
    private String employeeName;
    private String assetName;
    private String issueType;
    private String description;
    private LocalDate requestDate;
    private ServiceStatus serviceStatus;
}