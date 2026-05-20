package hexaware.casestudy.assetmanagement.repository;

import hexaware.casestudy.assetmanagement.entity.AuditResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditResponseRepository extends JpaRepository<AuditResponse, Long> {
    boolean existsByAuditRequestAuditRequestId(Long auditRequestId);
}