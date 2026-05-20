package hexaware.casestudy.assetmanagement.repository;

import hexaware.casestudy.assetmanagement.entity.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
    List<ReturnRequest> findByEmployeeUserId(Long userId);
}