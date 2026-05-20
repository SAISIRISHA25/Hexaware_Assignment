package hexaware.casestudy.assetmanagement.repository;

import hexaware.casestudy.assetmanagement.entity.AssetRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRequestRepository extends JpaRepository<AssetRequest, Long> {
    List<AssetRequest> findByEmployeeUserId(Long userId);
}