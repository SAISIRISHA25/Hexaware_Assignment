package hexaware.casestudy.assetmanagement.serviceimpl;

import hexaware.casestudy.assetmanagement.dto.request.AssetAllocationRequest;
import hexaware.casestudy.assetmanagement.dto.response.AssetAllocationResponse;
import hexaware.casestudy.assetmanagement.entity.*;
import hexaware.casestudy.assetmanagement.enums.AllocationStatus;
import hexaware.casestudy.assetmanagement.enums.AssetStatus;
import hexaware.casestudy.assetmanagement.enums.Role;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.exception.ResourceNotFoundException;
import hexaware.casestudy.assetmanagement.mapper.AssetAllocationMapper;
import hexaware.casestudy.assetmanagement.repository.*;
import hexaware.casestudy.assetmanagement.security.SecurityUtil;
import hexaware.casestudy.assetmanagement.service.AssetAllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetAllocationServiceImpl implements AssetAllocationService {

    private final AssetAllocationRepository allocationRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public AssetAllocationResponse allocateAsset(AssetAllocationRequest request) {
        log.info("Allocating asset id: {} to employee id: {}",
                request.getAssetId(), request.getEmployeeId());

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

        if (asset.getAssetStatus() == AssetStatus.ALLOCATED) {
            log.warn("Attempted to allocate already-allocated asset id: {}", asset.getAssetId());
            throw new BadRequestException("Asset is already allocated and not available for assignment");
        }

        User admin = securityUtil.getCurrentUser();

        AssetAllocation allocation = AssetAllocation.builder()
                .employee(employee)
                .asset(asset)
                .expectedReturnDate(request.getExpectedReturnDate())
                .allocatedByAdmin(admin)
                .remarks(request.getRemarks())
                .allocationStatus(AllocationStatus.ACTIVE)
                .build();

        asset.setAssetStatus(AssetStatus.ALLOCATED);
        assetRepository.save(asset);

        AssetAllocation savedAllocation = allocationRepository.save(allocation);
        log.info("Asset allocated successfully with allocation id: {}", savedAllocation.getAllocationId());
        return AssetAllocationMapper.toDto(savedAllocation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetAllocationResponse> getAllAllocations() {
        log.info("Fetching all allocations");
        return allocationRepository.findAll()
                .stream()
                .map(AssetAllocationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetAllocationResponse> getAllocationsByEmployee(Long employeeId) {
    
        User currentUser = securityUtil.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN
                && !currentUser.getUserId().equals(employeeId)) {
            throw new BadRequestException("Access denied: you can only view your own allocations.");
        }

        log.info("Fetching allocations for employee id: {}", employeeId);
        return allocationRepository.findByEmployeeUserId(employeeId)
                .stream()
                .map(AssetAllocationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public AssetAllocationResponse closeAllocation(Long allocationId) {
        log.info("Closing allocation with id: {}", allocationId);

        AssetAllocation allocation = allocationRepository.findById(allocationId)
                .orElseThrow(() -> {
                    log.error("Allocation not found with id: {}", allocationId);
                    return new ResourceNotFoundException("Allocation not found");
                });

        allocation.setAllocationStatus(AllocationStatus.CLOSED);
        allocation.setActualReturnDate(LocalDate.now());

        Asset asset = allocation.getAsset();
        asset.setAssetStatus(AssetStatus.AVAILABLE);
        assetRepository.save(asset);

        AssetAllocation updated = allocationRepository.save(allocation);
        log.info("Allocation closed successfully with id: {}", allocationId);
        return AssetAllocationMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetAllocationResponse> getActiveAllocationsByEmployee(Long employeeId) {
        User currentUser = securityUtil.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN
                && !currentUser.getUserId().equals(employeeId)) {
            throw new BadRequestException("Access denied: you can only view your own allocations.");
        }

        log.info("Fetching active allocations for employee id: {}", employeeId);
        return allocationRepository
                .findByEmployeeUserIdAndAllocationStatus(employeeId, AllocationStatus.ACTIVE)
                .stream()
                .map(AssetAllocationMapper::toDto)
                .toList();
    }
}
