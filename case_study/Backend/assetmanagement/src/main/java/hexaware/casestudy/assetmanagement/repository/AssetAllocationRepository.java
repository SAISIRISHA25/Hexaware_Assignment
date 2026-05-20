package hexaware.casestudy.assetmanagement.repository;

import hexaware.casestudy.assetmanagement.entity.AssetAllocation;
import hexaware.casestudy.assetmanagement.enums.AllocationStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetAllocationRepository extends JpaRepository<AssetAllocation, Long> {
    List<AssetAllocation> findByEmployeeUserId(Long userId);
    List<AssetAllocation> findByAssetAssetId(Long assetId);
    List<AssetAllocation> findByEmployeeUserIdAndAllocationStatus(
        Long userId,
        AllocationStatus status
);
}