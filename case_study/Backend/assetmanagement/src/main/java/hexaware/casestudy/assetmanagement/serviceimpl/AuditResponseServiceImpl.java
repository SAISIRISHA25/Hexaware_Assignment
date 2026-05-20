package hexaware.casestudy.assetmanagement.serviceimpl;

import hexaware.casestudy.assetmanagement.dto.request.AuditResponseDto;
import hexaware.casestudy.assetmanagement.dto.response.AuditResponseResponse;
import hexaware.casestudy.assetmanagement.entity.*;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.exception.ResourceNotFoundException;
import hexaware.casestudy.assetmanagement.mapper.AuditResponseMapper;
import hexaware.casestudy.assetmanagement.repository.*;
import hexaware.casestudy.assetmanagement.security.SecurityUtil;
import hexaware.casestudy.assetmanagement.service.AuditResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditResponseServiceImpl implements AuditResponseService {

    private final AuditResponseRepository auditResponseRepository;
    private final AuditRequestRepository auditRequestRepository;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public AuditResponseResponse submitAuditResponse(AuditResponseDto request) {

        log.info("Submitting audit response for audit request id: {}", request.getAuditRequestId());

        User employee = securityUtil.getCurrentUser();
        log.info("Audit response being submitted by employee id: {}", employee.getUserId());

        if (auditResponseRepository.existsByAuditRequestAuditRequestId(request.getAuditRequestId())) {
            log.warn("Duplicate audit response attempt for request id: {}", request.getAuditRequestId());
            throw new BadRequestException("Audit response already submitted for this request");
        }

        AuditRequest auditRequest = auditRequestRepository.findById(request.getAuditRequestId())
                .orElseThrow(() -> {
                    log.error("Audit request not found with id: {}", request.getAuditRequestId());
                    return new ResourceNotFoundException("Audit request not found");
                });

        AuditResponse response = AuditResponse.builder()
                .auditRequest(auditRequest)
                .employee(employee)
                .verificationStatus(request.getVerificationStatus())
                .comment(request.getComment())
                .build();

        auditRequest.setAuditStatus(request.getVerificationStatus());
        auditRequestRepository.save(auditRequest);

        AuditResponse saved = auditResponseRepository.save(response);
        log.info("Audit response submitted successfully with id: {}", saved.getAuditResponseId());

        return AuditResponseMapper.toDto(saved);
    }

    // FIX: @Override added — this method was missing from the AuditResponseService interface,
    // meaning it had no contract and could never be called via the service abstraction.
    // The interface has now been updated to include this method.
    @Override
    @Transactional(readOnly = true)
    public List<AuditResponseResponse> getAllAuditResponses() {
        log.info("Fetching all audit responses");
        return auditResponseRepository.findAll()
                .stream()
                .map(AuditResponseMapper::toDto)
                .toList();
    }
}
