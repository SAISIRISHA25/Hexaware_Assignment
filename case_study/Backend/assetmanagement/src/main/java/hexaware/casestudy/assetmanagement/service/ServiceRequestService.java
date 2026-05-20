package hexaware.casestudy.assetmanagement.service;

import hexaware.casestudy.assetmanagement.dto.request.ServiceRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.ServiceRequestResponse;
import hexaware.casestudy.assetmanagement.enums.ServiceStatus;
import java.util.List;

public interface ServiceRequestService {
    ServiceRequestResponse createServiceRequest(ServiceRequestDto request);
    List<ServiceRequestResponse> getAllServiceRequests();
    List<ServiceRequestResponse> getServiceRequestsByEmployee(Long employeeId);
    ServiceRequestResponse updateStatus(Long requestId, ServiceStatus status);
}