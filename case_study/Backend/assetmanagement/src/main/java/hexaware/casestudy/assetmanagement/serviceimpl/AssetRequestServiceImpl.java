package hexaware.casestudy.assetmanagement.serviceimpl;

import hexaware.casestudy.assetmanagement.dto.request.AssetRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.AssetRequestResponse;
import hexaware.casestudy.assetmanagement.entity.*;
import hexaware.casestudy.assetmanagement.enums.RequestStatus;
import hexaware.casestudy.assetmanagement.enums.Role;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.exception.ResourceNotFoundException;
import hexaware.casestudy.assetmanagement.mapper.AssetRequestMapper;
import hexaware.casestudy.assetmanagement.repository.*;
import hexaware.casestudy.assetmanagement.security.SecurityUtil;
import hexaware.casestudy.assetmanagement.service.AssetRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetRequestServiceImpl implements AssetRequestService {

    private final AssetRequestRepository assetRequestRepository;
    private final UserRepository userRepository;
    private final AssetCategoryRepository categoryRepository;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public AssetRequestResponse createRequest(AssetRequestDto request) {
        User employee = securityUtil.getCurrentUser();
        log.info("Creating asset request for employee id: {}", employee.getUserId());

        AssetCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", request.getCategoryId());
                    return new ResourceNotFoundException("Category not found");
                });

        AssetRequest assetRequest = AssetRequest.builder()
                .employee(employee)
                .category(category)
                .requestReason(request.getRequestReason())
                .requestStatus(RequestStatus.PENDING)
                .build();

        AssetRequest saved = assetRequestRepository.save(assetRequest);
        log.info("Asset request created successfully with id: {}", saved.getRequestId());
        return AssetRequestMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetRequestResponse> getAllRequests() {
        log.info("Fetching all asset requests");
        return assetRequestRepository.findAll()
                .stream()
                .map(AssetRequestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetRequestResponse> getRequestsByEmployee(Long employeeId) {
       
        User currentUser = securityUtil.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN
                && !currentUser.getUserId().equals(employeeId)) {
            throw new BadRequestException("Access denied: you can only view your own asset requests.");
        }

        log.info("Fetching asset requests for employee id: {}", employeeId);
        return assetRequestRepository.findByEmployeeUserId(employeeId)
                .stream()
                .map(AssetRequestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public AssetRequestResponse approveRequest(Long requestId) {
        User admin = securityUtil.getCurrentUser();
        log.info("Approving asset request id: {} by admin id: {}", requestId, admin.getUserId());

        AssetRequest request = assetRequestRepository.findById(requestId)
                .orElseThrow(() -> {
                    log.error("Asset request not found with id: {}", requestId);
                    return new ResourceNotFoundException("Asset request not found");
                });

        request.setRequestStatus(RequestStatus.APPROVED);
        request.setApprovedByAdmin(admin);

        AssetRequest updated = assetRequestRepository.save(request);
        log.info("Asset request approved successfully with id: {}", requestId);
        return AssetRequestMapper.toDto(updated);
    }

    @Override
    @Transactional
    public AssetRequestResponse rejectRequest(Long requestId) {
        User admin = securityUtil.getCurrentUser();
        log.info("Rejecting asset request id: {} by admin id: {}", requestId, admin.getUserId());

        AssetRequest request = assetRequestRepository.findById(requestId)
                .orElseThrow(() -> {
                    log.error("Asset request not found with id: {}", requestId);
                    return new ResourceNotFoundException("Asset request not found");
                });

        request.setRequestStatus(RequestStatus.REJECTED);
        request.setApprovedByAdmin(admin);

        AssetRequest updated = assetRequestRepository.save(request);
        log.info("Asset request rejected successfully with id: {}", requestId);
        return AssetRequestMapper.toDto(updated);
    }
}
