package hexaware.casestudy.assetmanagement.dto.response;

import hexaware.casestudy.assetmanagement.enums.RequestStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetRequestResponse {

    private Long requestId;
    private String employeeName;
    private String categoryName;
    private String requestReason;
    private LocalDate requestDate;
    private RequestStatus requestStatus;
}