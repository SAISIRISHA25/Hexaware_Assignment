package hexaware.casestudy.assetmanagement.entity;

import hexaware.casestudy.assetmanagement.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "asset_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssetRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private AssetCategory category;

    private String requestReason;

    private LocalDate requestDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @ManyToOne
    @JoinColumn(name = "approved_by_admin_id")
    private User approvedByAdmin;

    private String remarks;

    @PrePersist
    public void onCreate() {
        requestDate = LocalDate.now();
        if (requestStatus == null) {
            requestStatus = RequestStatus.PENDING;
        }
    }
}