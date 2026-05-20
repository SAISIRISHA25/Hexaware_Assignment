package hexaware.casestudy.assetmanagement.repository;

import hexaware.casestudy.assetmanagement.entity.AuditRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditRequestRepository extends JpaRepository<AuditRequest, Long> {
    List<AuditRequest> findByEmployeeUserId(Long userId);
    List<AuditRequest> findByAdminUserId(Long userId);
}