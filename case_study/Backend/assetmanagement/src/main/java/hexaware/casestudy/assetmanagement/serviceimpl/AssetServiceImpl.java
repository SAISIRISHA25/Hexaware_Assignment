package hexaware.casestudy.assetmanagement.serviceimpl;

import hexaware.casestudy.assetmanagement.dto.request.AssetCreateRequest;
import hexaware.casestudy.assetmanagement.dto.request.AssetUpdateRequest;
import hexaware.casestudy.assetmanagement.dto.response.AssetResponse;
import hexaware.casestudy.assetmanagement.entity.Asset;
import hexaware.casestudy.assetmanagement.entity.AssetCategory;
import hexaware.casestudy.assetmanagement.entity.User;
import hexaware.casestudy.assetmanagement.enums.AllocationStatus;
import hexaware.casestudy.assetmanagement.enums.AssetStatus;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.exception.ResourceNotFoundException;
import hexaware.casestudy.assetmanagement.mapper.AssetMapper;
import hexaware.casestudy.assetmanagement.repository.AssetAllocationRepository;
import hexaware.casestudy.assetmanagement.repository.AssetCategoryRepository;
import hexaware.casestudy.assetmanagement.repository.AssetRepository;
import hexaware.casestudy.assetmanagement.security.SecurityUtil;
import hexaware.casestudy.assetmanagement.service.AssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetCategoryRepository categoryRepository;
    private final AssetAllocationRepository allocationRepository;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public AssetResponse createAsset(AssetCreateRequest request) {
        log.info("Creating asset with assetNo: {}", request.getAssetNo());

        if (assetRepository.existsByAssetNo(request.getAssetNo())) {
            log.warn("Asset creation failed — assetNo already exists");
            throw new BadRequestException("Asset number already exists");
        }

        AssetCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", request.getCategoryId());
                    return new ResourceNotFoundException("Category not found");
                });

        User createdBy = securityUtil.getCurrentUser();

        Asset asset = Asset.builder()
                .assetNo(request.getAssetNo())
                .assetName(request.getAssetName())
                .assetModel(request.getAssetModel())
                .manufacturingDate(request.getManufacturingDate())
                .expiryDate(request.getExpiryDate())
                .assetValue(request.getAssetValue())
                .assetCondition(request.getAssetCondition())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .assetStatus(AssetStatus.AVAILABLE)
                .category(category)
                .createdBy(createdBy)
                .build();

        Asset saved = assetRepository.save(asset);
        log.info("Asset created successfully with id: {}", saved.getAssetId());
        return AssetMapper.toDto(saved);
    }

    
    @Override
    @Transactional(readOnly = true)
    public Page<AssetResponse> getAllAssets(Pageable pageable) {
        log.info("Fetching assets page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return assetRepository.findByDeletedFalse(pageable)
                .map(AssetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public AssetResponse getAssetById(Long id) {
        log.info("Fetching asset with id: {}", id);
        Asset asset = assetRepository.findByAssetIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.error("Asset not found with id: {}", id);
                    return new ResourceNotFoundException("Asset not found");
                });
        return AssetMapper.toDto(asset);
    }

    @Override
    @Transactional
    public AssetResponse updateAsset(Long id, AssetUpdateRequest request) {
        log.info("Updating asset with id: {}", id);

        Asset asset = assetRepository.findByAssetIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.error("Asset not found with id: {}", id);
                    return new ResourceNotFoundException("Asset not found");
                });

        AssetCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", request.getCategoryId());
                    return new ResourceNotFoundException("Category not found");
                });

        asset.setAssetName(request.getAssetName());
        asset.setAssetModel(request.getAssetModel());
        asset.setManufacturingDate(request.getManufacturingDate());
        asset.setExpiryDate(request.getExpiryDate());
        asset.setAssetValue(request.getAssetValue());
        asset.setAssetCondition(request.getAssetCondition());
        asset.setDescription(request.getDescription());
        asset.setImageUrl(request.getImageUrl());
        asset.setCategory(category);

        Asset updated = assetRepository.save(asset);
        log.info("Asset updated successfully with id: {}", id);
        return AssetMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteAsset(Long id) {
        log.info("Soft-deleting asset with id: {}", id);

        Asset asset = assetRepository.findByAssetIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.error("Asset not found with id: {}", id);
                    return new ResourceNotFoundException("Asset not found");
                });

        if (asset.getAssetStatus() == AssetStatus.ALLOCATED) {
            throw new BadRequestException(
                    "Cannot delete an asset that is currently allocated. Close the allocation first.");
        }

        boolean hasActiveAllocations = allocationRepository
                .findByAssetAssetId(asset.getAssetId())
                .stream()
                .anyMatch(a -> a.getAllocationStatus() == AllocationStatus.ACTIVE);

        if (hasActiveAllocations) {
            throw new BadRequestException("Cannot delete an asset with active allocations.");
        }

        assetRepository.softDeleteById(id);
        log.info("Asset soft-deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetResponse> getAssetsByCategory(Long categoryId) {
        log.info("Fetching assets for category id: {}", categoryId);
        return assetRepository.findByCategoryCategoryId(categoryId)
                .stream()
                .map(AssetMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetResponse> searchAssets(String keyword) {
        log.info("Searching assets with keyword: {}", keyword);
        return assetRepository.findByAssetNameContainingIgnoreCase(keyword)
                .stream()
                .map(AssetMapper::toDto)
                .toList();
    }
}
