package hexaware.casestudy.assetmanagement.controller;

import hexaware.casestudy.assetmanagement.dto.request.AssetCategoryRequest;
import hexaware.casestudy.assetmanagement.dto.response.ApiResponse;
import hexaware.casestudy.assetmanagement.dto.response.AssetCategoryResponse;
import hexaware.casestudy.assetmanagement.service.AssetCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Asset Categories", description = "Manage asset categories. Admin only.")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class AssetCategoryController {

    private final AssetCategoryService categoryService;

    @Operation(summary = "Create category", description = "Creates a new asset category. Admin only.")
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> create(
            @Valid @RequestBody AssetCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Category created successfully",
                        categoryService.createCategory(request)));
    }

    @Operation(summary = "Get all categories", description = "Returns all active asset categories.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<AssetCategoryResponse>>> getAll() {
        return ResponseEntity.ok(
                ApiResponse.success("Categories fetched successfully",
                        categoryService.getAllCategories()));
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Category fetched successfully",
                        categoryService.getCategoryById(id)));
    }

    @Operation(summary = "Update category", description = "Updates an existing asset category. Admin only.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody AssetCategoryRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Category updated successfully",
                        categoryService.updateCategory(id, request)));
    }

    @Operation(summary = "Delete category", description = "Soft-deletes a category. Blocked if active assets exist for this category. Admin only.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                ApiResponse.success("Category deleted successfully", null));
    }
}
