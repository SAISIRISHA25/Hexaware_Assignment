package hexaware.casestudy.assetmanagement.dto.response;

import hexaware.casestudy.assetmanagement.enums.AllocationStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetAllocationResponse {

    private Long allocationId;
    private Long assetId;
    private String employeeName;
    private String assetName;
    private LocalDate allocatedDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private AllocationStatus allocationStatus;
}