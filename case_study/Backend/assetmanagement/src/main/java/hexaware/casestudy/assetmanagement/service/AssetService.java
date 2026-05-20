package hexaware.casestudy.assetmanagement.service;

import hexaware.casestudy.assetmanagement.dto.request.AssetCreateRequest;
import hexaware.casestudy.assetmanagement.dto.request.AssetUpdateRequest;
import hexaware.casestudy.assetmanagement.dto.response.AssetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetService {

    AssetResponse createAsset(AssetCreateRequest request);

    // FIX: Pageable overload added. Original returned a raw List<> — with thousands of assets
    // this would fetch everything from the DB in one query, consuming excessive memory.
    // Use GET /api/v1/assets?page=0&size=20&sort=assetId to page through results.
    Page<AssetResponse> getAllAssets(Pageable pageable);

    AssetResponse getAssetById(Long id);

    AssetResponse updateAsset(Long id, AssetUpdateRequest request);

    void deleteAsset(Long id);

    List<AssetResponse> getAssetsByCategory(Long categoryId);

    List<AssetResponse> searchAssets(String keyword);
}
