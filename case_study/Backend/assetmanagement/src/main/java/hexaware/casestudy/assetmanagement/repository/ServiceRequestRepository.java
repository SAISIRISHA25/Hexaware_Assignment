package hexaware.casestudy.assetmanagement.repository;

import hexaware.casestudy.assetmanagement.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByEmployeeUserId(Long userId);
}