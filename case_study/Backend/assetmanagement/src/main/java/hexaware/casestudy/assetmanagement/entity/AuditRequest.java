package hexaware.casestudy.assetmanagement.entity;

import hexaware.casestudy.assetmanagement.enums.AuditStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "audit_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditRequestId;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    private LocalDate auditDate;

    @Enumerated(EnumType.STRING)
    private AuditStatus auditStatus;

    private String remarks;

    @PrePersist
    public void onCreate() {
        auditDate = LocalDate.now();
        if (auditStatus == null) {
            auditStatus = AuditStatus.PENDING;
        }
    }
}