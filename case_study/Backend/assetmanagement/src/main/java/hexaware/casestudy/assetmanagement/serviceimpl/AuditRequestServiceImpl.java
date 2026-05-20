package hexaware.casestudy.assetmanagement.serviceimpl;

import hexaware.casestudy.assetmanagement.dto.request.AuditRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.AuditRequestResponse;
import hexaware.casestudy.assetmanagement.entity.*;
import hexaware.casestudy.assetmanagement.enums.AuditStatus;
import hexaware.casestudy.assetmanagement.enums.Role;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.exception.ResourceNotFoundException;
import hexaware.casestudy.assetmanagement.mapper.AuditRequestMapper;
import hexaware.casestudy.assetmanagement.repository.*;
import hexaware.casestudy.assetmanagement.security.SecurityUtil;
import hexaware.casestudy.assetmanagement.service.AuditRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditRequestServiceImpl implements AuditRequestService {

    private final AuditRequestRepository auditRequestRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public AuditRequestResponse createAuditRequest(AuditRequestDto request) {
        User admin = securityUtil.getCurrentUser();
        log.info("Creating audit request by admin id: {} for employee id: {}",
                admin.getUserId(), request.getEmployeeId());

        User employee = userRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", request.getEmployeeId());
                    return new ResourceNotFoundException("Employee not found");
                });

        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(() -> {
                    log.error("Asset not found with id: {}", request.getAssetId());
                    return new ResourceNotFoundException("Asset not found");
                });

        AuditRequest auditRequest = AuditRequest.builder()
                .admin(admin)
                .employee(employee)
                .asset(asset)
                .remarks(request.getRemarks())
                .auditStatus(AuditStatus.PENDING)
                .build();

        AuditRequest saved = auditRequestRepository.save(auditRequest);
        log.info("Audit request created successfully with id: {}", saved.getAuditRequestId());
        return AuditRequestMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditRequestResponse> getAllAuditRequests() {
        log.info("Fetching all audit requests");
        return auditRequestRepository.findAll()
                .stream()
                .map(AuditRequestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditRequestResponse> getAuditRequestsByEmployee(Long employeeId) {
        User currentUser = securityUtil.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN
                && !currentUser.getUserId().equals(employeeId)) {
            throw new BadRequestException("Access denied: you can only view your own audit requests.");
        }

        log.info("Fetching audit requests for employee id: {}", employeeId);
        return auditRequestRepository.findByEmployeeUserId(employeeId)
                .stream()
                .map(AuditRequestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditRequestResponse> getAuditRequestsByAdmin(Long adminId) {
        log.info("Fetching audit requests for admin id: {}", adminId);
        return auditRequestRepository.findByAdminUserId(adminId)
                .stream()
                .map(AuditRequestMapper::toDto)
                .toList();
    }
}
