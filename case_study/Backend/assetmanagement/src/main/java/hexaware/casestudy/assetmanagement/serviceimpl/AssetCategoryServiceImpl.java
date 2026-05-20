package hexaware.casestudy.assetmanagement.serviceimpl;

import hexaware.casestudy.assetmanagement.dto.request.AssetCategoryRequest;
import hexaware.casestudy.assetmanagement.dto.response.AssetCategoryResponse;
import hexaware.casestudy.assetmanagement.entity.AssetCategory;
import hexaware.casestudy.assetmanagement.enums.CategoryStatus;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.exception.ResourceNotFoundException;
import hexaware.casestudy.assetmanagement.mapper.AssetCategoryMapper;
import hexaware.casestudy.assetmanagement.repository.AssetCategoryRepository;
import hexaware.casestudy.assetmanagement.service.AssetCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetCategoryServiceImpl implements AssetCategoryService {

    private final AssetCategoryRepository categoryRepository;

    @Override
    @Transactional
    public AssetCategoryResponse createCategory(AssetCategoryRequest request) {
        log.info("Creating category: {}", request.getCategoryName());

        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            log.warn("Category creation failed — name already exists");
            throw new BadRequestException("Category already exists");
        }

        AssetCategory category = AssetCategory.builder()
                .categoryName(request.getCategoryName())
                .description(request.getDescription())
                .status(CategoryStatus.ACTIVE)   // FIX: was hardcoded string "ACTIVE"
                .build();

        AssetCategory saved = categoryRepository.save(category);
        log.info("Category created successfully with id: {}", saved.getCategoryId());
        return AssetCategoryMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetCategoryResponse> getAllCategories() {
        log.info("Fetching all categories");
        return categoryRepository.findByDeletedFalse()
                .stream()
                .map(AssetCategoryMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AssetCategoryResponse getCategoryById(Long id) {
        log.info("Fetching category with id: {}", id);
        AssetCategory category = categoryRepository.findByCategoryIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", id);
                    return new ResourceNotFoundException("Category not found");
                });
        return AssetCategoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public AssetCategoryResponse updateCategory(Long id, AssetCategoryRequest request) {
        log.info("Updating category with id: {}", id);

        AssetCategory category = categoryRepository.findByCategoryIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", id);
                    return new ResourceNotFoundException("Category not found");
                });

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        AssetCategory updated = categoryRepository.save(category);
        log.info("Category updated successfully with id: {}", id);
        return AssetCategoryMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        log.info("Soft-deleting category with id: {}", id);

        if (!categoryRepository.existsByCategoryIdAndDeletedFalse(id)) {
            log.error("Category not found with id: {}", id);
            throw new ResourceNotFoundException("Category not found");
        }

        if (categoryRepository.hasActiveAssets(id)) {
            throw new BadRequestException(
                    "Cannot delete a category that has active assets assigned to it. " +
                            "Please reassign or delete those assets first.");
        }

        categoryRepository.softDeleteById(id);
        log.info("Category soft-deleted successfully with id: {}", id);
    }
}
