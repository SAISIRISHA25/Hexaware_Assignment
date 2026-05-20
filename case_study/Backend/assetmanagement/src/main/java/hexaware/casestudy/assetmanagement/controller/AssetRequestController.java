package hexaware.casestudy.assetmanagement.controller;

import hexaware.casestudy.assetmanagement.dto.request.AssetRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.ApiResponse;
import hexaware.casestudy.assetmanagement.service.AssetRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Asset Requests", description = "Employees request assets; Admins approve or reject.")
@RestController
@RequestMapping("/api/v1/asset-requests")
@RequiredArgsConstructor
public class AssetRequestController {

    private final AssetRequestService assetRequestService;

    @Operation(summary = "Create asset request", description = "Employee raises a new asset request for a given category.")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody AssetRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Asset request created", assetRequestService.createRequest(request)));
    }

    @Operation(summary = "Get all asset requests", description = "Returns all asset requests. Admin only.")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Asset requests fetched", assetRequestService.getAllRequests()));
    }

    @Operation(summary = "Get requests by employee", description = "Returns asset requests for a specific employee.")
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ApiResponse.success("Employee asset requests fetched",
                assetRequestService.getRequestsByEmployee(employeeId)));
    }

    @Operation(summary = "Approve asset request", description = "Approves a pending asset request. Admin only.")
    @PutMapping("/{requestId}/approve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> approve(@PathVariable Long requestId) {
        return ResponseEntity.ok(ApiResponse.success("Asset request approved",
                assetRequestService.approveRequest(requestId)));
    }

    @Operation(summary = "Reject asset request", description = "Rejects a pending asset request. Admin only.")
    @PutMapping("/{requestId}/reject")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> reject(@PathVariable Long requestId) {
        return ResponseEntity.ok(ApiResponse.success("Asset request rejected",
                assetRequestService.rejectRequest(requestId)));
    }
}
