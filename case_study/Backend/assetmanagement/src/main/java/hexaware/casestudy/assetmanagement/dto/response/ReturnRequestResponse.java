package hexaware.casestudy.assetmanagement.dto.response;

import hexaware.casestudy.assetmanagement.enums.RequestStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnRequestResponse {

    private Long returnRequestId;
    private String employeeName;
    private String assetName;
    private LocalDate requestDate;
    private RequestStatus returnStatus;
}