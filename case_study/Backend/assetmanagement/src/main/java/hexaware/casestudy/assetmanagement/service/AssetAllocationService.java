package hexaware.casestudy.assetmanagement.service;

import hexaware.casestudy.assetmanagement.dto.request.AssetAllocationRequest;
import hexaware.casestudy.assetmanagement.dto.response.AssetAllocationResponse;
import java.util.List;

public interface AssetAllocationService {
    AssetAllocationResponse allocateAsset(AssetAllocationRequest request);
    List<AssetAllocationResponse> getAllAllocations();
    List<AssetAllocationResponse> getAllocationsByEmployee(Long employeeId);
    AssetAllocationResponse closeAllocation(Long allocationId);
    List<AssetAllocationResponse> getActiveAllocationsByEmployee(Long employeeId);
}