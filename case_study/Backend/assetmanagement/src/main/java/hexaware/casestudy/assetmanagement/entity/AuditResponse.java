package hexaware.casestudy.assetmanagement.entity;

import hexaware.casestudy.assetmanagement.enums.AuditStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "audit_responses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditResponseId;

    @OneToOne
    @JoinColumn(name = "audit_request_id", nullable = false)
    private AuditRequest auditRequest;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    private LocalDate responseDate;

    @Enumerated(EnumType.STRING)
    private AuditStatus verificationStatus;

    private String comment;

    @PrePersist
    public void onCreate() {
        responseDate = LocalDate.now();
    }
}