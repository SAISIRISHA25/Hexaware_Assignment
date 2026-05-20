package hexaware.casestudy.assetmanagement.service;

import hexaware.casestudy.assetmanagement.dto.request.AuditResponseDto;
import hexaware.casestudy.assetmanagement.dto.response.AuditResponseResponse;

import java.util.List;

public interface AuditResponseService {

    AuditResponseResponse submitAuditResponse(AuditResponseDto request);

    // FIX: getAllAuditResponses() was implemented in AuditResponseServiceImpl but was
    // missing from this interface, breaking the service-layer contract and meaning
    // the method could never be called through the service abstraction.
    List<AuditResponseResponse> getAllAuditResponses();
}
