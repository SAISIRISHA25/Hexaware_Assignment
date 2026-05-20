package hexaware.casestudy.assetmanagement.controller;

import hexaware.casestudy.assetmanagement.dto.request.AssetAllocationRequest;
import hexaware.casestudy.assetmanagement.dto.response.ApiResponse;
import hexaware.casestudy.assetmanagement.security.SecurityUtil;
import hexaware.casestudy.assetmanagement.service.AssetAllocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Asset Allocations", description = "Admin allocates assets to employees. Employees view their own allocated assets.")
@RestController
@RequestMapping("/api/v1/allocations")
@RequiredArgsConstructor
public class AssetAllocationController {

    private final AssetAllocationService allocationService;
    private final SecurityUtil securityUtil;

    @Operation(summary = "Allocate asset to employee",
            description = "Admin assigns an available asset to an employee. Admin only.")
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> allocate(@Valid @RequestBody AssetAllocationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Asset allocated successfully", allocationService.allocateAsset(request)));
    }

    @Operation(summary = "Get all allocations",
            description = "Returns all asset allocations across all employees. Admin only.")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Allocations fetched", allocationService.getAllAllocations()));
    }

    
    @Operation(summary = "Get my allocations",
            description = "Returns all asset allocations for the currently authenticated employee (resolved from JWT).")
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> getMyAllocations() {
        Long currentUserId = securityUtil.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("My allocations fetched",
                allocationService.getAllocationsByEmployee(currentUserId)));
    }

    @Operation(summary = "Get my active allocations",
            description = "Returns only ACTIVE allocations for the currently authenticated employee.")
    @GetMapping("/me/active")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> getMyActiveAllocations() {
        Long currentUserId = securityUtil.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("My active allocations fetched",
                allocationService.getActiveAllocationsByEmployee(currentUserId)));
    }

    @Operation(summary = "Get allocations by employee ID",
            description = "Returns all allocations for a specific employee. Admin only.")
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ApiResponse.success("Employee allocations fetched",
                allocationService.getAllocationsByEmployee(employeeId)));
    }

    @Operation(summary = "Get active allocations by employee ID", description = "Admin only.")
    @GetMapping("/employee/{employeeId}/active")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getActiveByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ApiResponse.success("Active allocations fetched",
                allocationService.getActiveAllocationsByEmployee(employeeId)));
    }

    @Operation(summary = "Close allocation",
            description = "Marks an allocation as CLOSED and sets the asset back to AVAILABLE. Admin only.")
    @PutMapping("/{allocationId}/close")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> close(@PathVariable Long allocationId) {
        return ResponseEntity.ok(ApiResponse.success("Allocation closed",
                allocationService.closeAllocation(allocationId)));
    }
}
