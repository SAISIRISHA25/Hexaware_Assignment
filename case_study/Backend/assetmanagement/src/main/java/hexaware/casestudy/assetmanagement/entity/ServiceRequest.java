package hexaware.casestudy.assetmanagement.entity;

import hexaware.casestudy.assetmanagement.enums.ServiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "service_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceRequestId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    private String issueType;

    private String description;

    private LocalDate requestDate;

    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;

    @ManyToOne
    @JoinColumn(name = "updated_by_admin_id")
    private User updatedByAdmin;

    @PrePersist
    public void onCreate() {
        requestDate = LocalDate.now();
        if (serviceStatus == null) {
            serviceStatus = ServiceStatus.PENDING;
        }
    }
}