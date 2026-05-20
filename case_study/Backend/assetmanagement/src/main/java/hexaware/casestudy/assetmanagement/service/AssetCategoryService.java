package hexaware.casestudy.assetmanagement.service;

import hexaware.casestudy.assetmanagement.dto.request.AssetCategoryRequest;
import hexaware.casestudy.assetmanagement.dto.response.AssetCategoryResponse;
import java.util.List;

public interface AssetCategoryService {
    AssetCategoryResponse createCategory(AssetCategoryRequest request);
    List<AssetCategoryResponse> getAllCategories();
    AssetCategoryResponse getCategoryById(Long id);
    AssetCategoryResponse updateCategory(Long id, AssetCategoryRequest request);
    void deleteCategory(Long id);
}