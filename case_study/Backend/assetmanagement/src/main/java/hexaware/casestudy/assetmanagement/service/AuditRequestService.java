package hexaware.casestudy.assetmanagement.service;

import hexaware.casestudy.assetmanagement.dto.request.AuditRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.AuditRequestResponse;
import java.util.List;

public interface AuditRequestService {
    AuditRequestResponse createAuditRequest(AuditRequestDto request);
    List<AuditRequestResponse> getAllAuditRequests();
    List<AuditRequestResponse> getAuditRequestsByEmployee(Long employeeId);
    List<AuditRequestResponse> getAuditRequestsByAdmin(Long adminId);
}