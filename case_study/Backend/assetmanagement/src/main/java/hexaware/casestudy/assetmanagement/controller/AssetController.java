package hexaware.casestudy.assetmanagement.controller;

import hexaware.casestudy.assetmanagement.dto.request.AssetCreateRequest;
import hexaware.casestudy.assetmanagement.dto.request.AssetUpdateRequest;
import hexaware.casestudy.assetmanagement.dto.response.ApiResponse;
import hexaware.casestudy.assetmanagement.dto.response.AssetResponse;
import hexaware.casestudy.assetmanagement.service.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Assets", description = "Asset catalogue management. Admins manage assets; employees browse.")
@Validated  // required for @Size on @RequestParam to be evaluated
@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @Operation(summary = "Create asset",
            description = "Admin adds a new asset to the catalogue.")
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<AssetResponse>> createAsset(
            @Valid @RequestBody AssetCreateRequest request) {
        AssetResponse response = assetService.createAsset(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Asset created successfully", response));
    }

    
    @Operation(summary = "Get all assets (paginated)",
            description = "Returns paginated asset catalogue. Supports ?page=0&size=20&sort=assetName. Both roles.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<Page<AssetResponse>>> getAllAssets(
            @PageableDefault(size = 20, sort = "assetId") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Assets fetched successfully",
                assetService.getAllAssets(pageable)));
    }

    @Operation(summary = "Get asset by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<AssetResponse>> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Asset fetched successfully",
                assetService.getAssetById(id)));
    }

    @Operation(summary = "Update asset",
            description = "Admin updates asset details. assetNo cannot be changed. Admin only.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<AssetResponse>> updateAsset(
            @PathVariable Long id,
            @Valid @RequestBody AssetUpdateRequest request) {
        AssetResponse response = assetService.updateAsset(id, request);
        return ResponseEntity.ok(ApiResponse.success("Asset updated successfully", response));
    }

    @Operation(summary = "Delete asset",
            description = "Soft-deletes an asset. Blocked if asset is currently allocated. Admin only.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return ResponseEntity.ok(ApiResponse.success("Asset deleted successfully", null));
    }

    @Operation(summary = "Get assets by category")
    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<AssetResponse>>> getByCategory(
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(ApiResponse.success("Assets fetched successfully",
                assetService.getAssetsByCategory(categoryId)));
    }

    
    @Operation(summary = "Search assets by name",
            description = "Case-insensitive search on asset name. keyword must be 1–100 characters.")
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<AssetResponse>>> searchAssets(
            @RequestParam @Size(min = 1, max = 100, message = "Search keyword must be between 1 and 100 characters") String keyword) {
        return ResponseEntity.ok(ApiResponse.success("Search results",
                assetService.searchAssets(keyword)));
    }
}
