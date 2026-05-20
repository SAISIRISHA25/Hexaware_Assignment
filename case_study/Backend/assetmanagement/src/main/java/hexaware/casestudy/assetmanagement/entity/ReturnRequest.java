package hexaware.casestudy.assetmanagement.entity;

import hexaware.casestudy.assetmanagement.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "return_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReturnRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnRequestId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "allocation_id", nullable = false)
    private AssetAllocation allocation;

    private LocalDate requestDate;

    private String returnReason;

    @Enumerated(EnumType.STRING)
    private RequestStatus returnStatus;

    @ManyToOne
    @JoinColumn(name = "approved_by_admin_id")
    private User approvedByAdmin;

    @PrePersist
    public void onCreate() {
        requestDate = LocalDate.now();
        if (returnStatus == null) {
            returnStatus = RequestStatus.PENDING;
        }
    }
}