package hexaware.casestudy.assetmanagement.service;

import hexaware.casestudy.assetmanagement.dto.request.ReturnRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.ReturnRequestResponse;
import java.util.List;

public interface ReturnRequestService {
    ReturnRequestResponse createReturnRequest(ReturnRequestDto request);
    List<ReturnRequestResponse> getAllReturnRequests();
    List<ReturnRequestResponse> getReturnRequestsByEmployee(Long employeeId);
    ReturnRequestResponse approveReturn(Long returnRequestId);
    ReturnRequestResponse rejectReturn(Long returnRequestId);
}