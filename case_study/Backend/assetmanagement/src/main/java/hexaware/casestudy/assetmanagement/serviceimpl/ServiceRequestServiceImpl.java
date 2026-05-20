package hexaware.casestudy.assetmanagement.serviceimpl;

import hexaware.casestudy.assetmanagement.dto.request.ServiceRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.ServiceRequestResponse;
import hexaware.casestudy.assetmanagement.entity.*;
import hexaware.casestudy.assetmanagement.enums.AssetStatus;
import hexaware.casestudy.assetmanagement.enums.Role;
import hexaware.casestudy.assetmanagement.enums.ServiceStatus;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.exception.ResourceNotFoundException;
import hexaware.casestudy.assetmanagement.mapper.ServiceRequestMapper;
import hexaware.casestudy.assetmanagement.repository.*;
import hexaware.casestudy.assetmanagement.security.SecurityUtil;
import hexaware.casestudy.assetmanagement.service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public ServiceRequestResponse createServiceRequest(ServiceRequestDto request) {
        User employee = securityUtil.getCurrentUser();
        log.info("Creating service request for employee id: {}", employee.getUserId());

        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(() -> {
                    log.error("Asset not found with id: {}", request.getAssetId());
                    return new ResourceNotFoundException("Asset not found");
                });

        ServiceRequest serviceRequest = ServiceRequest.builder()
                .employee(employee)
                .asset(asset)
                .issueType(request.getIssueType())
                .description(request.getDescription())
                .serviceStatus(ServiceStatus.PENDING)
                .build();

        // FIX (critical): Set asset to UNDER_SERVICE so it cannot be allocated to others
        // while awaiting repair. The enum value existed but was never used.
        asset.setAssetStatus(AssetStatus.UNDER_SERVICE);
        assetRepository.save(asset);
        log.info("Asset id: {} marked as UNDER_SERVICE", asset.getAssetId());

        ServiceRequest saved = serviceRequestRepository.save(serviceRequest);
        log.info("Service request created successfully with id: {}", saved.getServiceRequestId());
        return ServiceRequestMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRequestResponse> getAllServiceRequests() {
        log.info("Fetching all service requests");
        return serviceRequestRepository.findAll()
                .stream()
                .map(ServiceRequestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRequestResponse> getServiceRequestsByEmployee(Long employeeId) {
        // FIX (security): IDOR guard — employees may only access their own service requests.
        User currentUser = securityUtil.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN
                && !currentUser.getUserId().equals(employeeId)) {
            throw new BadRequestException("Access denied: you can only view your own service requests.");
        }

        log.info("Fetching service requests for employee id: {}", employeeId);
        return serviceRequestRepository.findByEmployeeUserId(employeeId)
                .stream()
                .map(ServiceRequestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ServiceRequestResponse updateStatus(Long requestId, ServiceStatus status) {
        User admin = securityUtil.getCurrentUser();
        log.info("Updating service request id: {} to status: {} by admin id: {}",
                requestId, status, admin.getUserId());

        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> {
                    log.error("Service request not found with id: {}", requestId);
                    return new ResourceNotFoundException("Service request not found");
                });

        request.setServiceStatus(status);
        request.setUpdatedByAdmin(admin);

        // FIX (critical): Revert asset status when service is concluded.
        // COMPLETED → asset back and available for allocation.
        // REJECTED  → service not accepted; revert so asset is usable again.
        if (status == ServiceStatus.COMPLETED || status == ServiceStatus.REJECTED) {
            Asset asset = request.getAsset();
            asset.setAssetStatus(AssetStatus.AVAILABLE);
            assetRepository.save(asset);
            log.info("Asset id: {} reverted to AVAILABLE after service status: {}",
                    asset.getAssetId(), status);
        }

        ServiceRequest updated = serviceRequestRepository.save(request);
        log.info("Service request updated successfully with id: {}", requestId);
        return ServiceRequestMapper.toDto(updated);
    }
}
