package hexaware.casestudy.assetmanagement.entity;

import hexaware.casestudy.assetmanagement.enums.AllocationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "asset_allocations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssetAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long allocationId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    private LocalDate allocatedDate;

    private LocalDate expectedReturnDate;

    private LocalDate actualReturnDate;

    @Enumerated(EnumType.STRING)
    private AllocationStatus allocationStatus;

    @ManyToOne
    @JoinColumn(name = "allocated_by_admin_id")
    private User allocatedByAdmin;

    private String remarks;

    @PrePersist
    public void onCreate() {
        allocatedDate = LocalDate.now();
        if (allocationStatus == null) {
            allocationStatus = AllocationStatus.ACTIVE;
        }
    }
}