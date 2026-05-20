package hexaware.casestudy.assetmanagement.serviceimpl;

import hexaware.casestudy.assetmanagement.dto.request.ReturnRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.ReturnRequestResponse;
import hexaware.casestudy.assetmanagement.entity.*;
import hexaware.casestudy.assetmanagement.enums.AllocationStatus;
import hexaware.casestudy.assetmanagement.enums.AssetStatus;
import hexaware.casestudy.assetmanagement.enums.RequestStatus;
import hexaware.casestudy.assetmanagement.enums.Role;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.exception.ResourceNotFoundException;
import hexaware.casestudy.assetmanagement.mapper.ReturnRequestMapper;
import hexaware.casestudy.assetmanagement.repository.*;
import hexaware.casestudy.assetmanagement.security.SecurityUtil;
import hexaware.casestudy.assetmanagement.service.ReturnRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReturnRequestServiceImpl implements ReturnRequestService {

    private final ReturnRequestRepository returnRequestRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final AssetAllocationRepository allocationRepository;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public ReturnRequestResponse createReturnRequest(ReturnRequestDto request) {
        User employee = securityUtil.getCurrentUser();
        log.info("Creating return request for employee id: {}", employee.getUserId());

        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(() -> {
                    log.error("Asset not found with id: {}", request.getAssetId());
                    return new ResourceNotFoundException("Asset not found");
                });

        AssetAllocation allocation = allocationRepository.findById(request.getAllocationId())
                .orElseThrow(() -> {
                    log.error("Allocation not found with id: {}", request.getAllocationId());
                    return new ResourceNotFoundException("Allocation not found");
                });

        ReturnRequest returnRequest = ReturnRequest.builder()
                .employee(employee)
                .asset(asset)
                .allocation(allocation)
                .returnReason(request.getReturnReason())
                .returnStatus(RequestStatus.PENDING)
                .build();

        ReturnRequest saved = returnRequestRepository.save(returnRequest);
        log.info("Return request created successfully with id: {}", saved.getReturnRequestId());
        return ReturnRequestMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReturnRequestResponse> getAllReturnRequests() {
        log.info("Fetching all return requests");
        return returnRequestRepository.findAll()
                .stream()
                .map(ReturnRequestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReturnRequestResponse> getReturnRequestsByEmployee(Long employeeId) {
        // FIX: IDOR guard — employees may only access their own return requests.
        User currentUser = securityUtil.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN
                && !currentUser.getUserId().equals(employeeId)) {
            throw new BadRequestException("Access denied: you can only view your own return requests.");
        }

        log.info("Fetching return requests for employee id: {}", employeeId);
        return returnRequestRepository.findByEmployeeUserId(employeeId)
                .stream()
                .map(ReturnRequestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ReturnRequestResponse approveReturn(Long returnRequestId) {
        User admin = securityUtil.getCurrentUser();
        log.info("Approving return request id: {} by admin id: {}", returnRequestId, admin.getUserId());

        ReturnRequest request = returnRequestRepository.findById(returnRequestId)
                .orElseThrow(() -> {
                    log.error("Return request not found with id: {}", returnRequestId);
                    return new ResourceNotFoundException("Return request not found");
                });

        request.setReturnStatus(RequestStatus.APPROVED);
        request.setApprovedByAdmin(admin);

        Asset asset = request.getAsset();
        asset.setAssetStatus(AssetStatus.AVAILABLE);
        assetRepository.save(asset);

        AssetAllocation allocation = request.getAllocation();
        allocation.setAllocationStatus(AllocationStatus.RETURNED);
        allocationRepository.save(allocation);

        ReturnRequest updated = returnRequestRepository.save(request);
        log.info("Return request approved successfully with id: {}", returnRequestId);
        return ReturnRequestMapper.toDto(updated);
    }

    @Override
    @Transactional
    public ReturnRequestResponse rejectReturn(Long returnRequestId) {
        User admin = securityUtil.getCurrentUser();
        log.info("Rejecting return request id: {} by admin id: {}", returnRequestId, admin.getUserId());

        ReturnRequest request = returnRequestRepository.findById(returnRequestId)
                .orElseThrow(() -> {
                    log.error("Return request not found with id: {}", returnRequestId);
                    return new ResourceNotFoundException("Return request not found");
                });

        request.setReturnStatus(RequestStatus.REJECTED);
        request.setApprovedByAdmin(admin);

        ReturnRequest updated = returnRequestRepository.save(request);
        log.info("Return request rejected successfully with id: {}", returnRequestId);
        return ReturnRequestMapper.toDto(updated);
    }
}
