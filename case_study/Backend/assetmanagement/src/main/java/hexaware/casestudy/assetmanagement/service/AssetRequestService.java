package hexaware.casestudy.assetmanagement.service;

import hexaware.casestudy.assetmanagement.dto.request.AssetRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.AssetRequestResponse;
import java.util.List;

public interface AssetRequestService {
    AssetRequestResponse createRequest(AssetRequestDto request);
    List<AssetRequestResponse> getAllRequests();
    List<AssetRequestResponse> getRequestsByEmployee(Long employeeId);
    AssetRequestResponse approveRequest(Long requestId);
    AssetRequestResponse rejectRequest(Long requestId);
}