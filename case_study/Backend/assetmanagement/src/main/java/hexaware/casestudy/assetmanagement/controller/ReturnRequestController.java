package hexaware.casestudy.assetmanagement.controller;

import hexaware.casestudy.assetmanagement.dto.request.ReturnRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.ApiResponse;
import hexaware.casestudy.assetmanagement.service.ReturnRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Return Requests", description = "Employees request to return assets; Admins approve or reject.")
@RestController
@RequestMapping("/api/v1/return-requests")
@RequiredArgsConstructor
public class ReturnRequestController {

    private final ReturnRequestService service;

    @Operation(summary = "Create return request", description = "Employee raises a return request for an allocated asset.")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody ReturnRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Return request created", service.createReturnRequest(request)));
    }

    @Operation(summary = "Get all return requests", description = "Returns all return requests. Admin only.")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Return requests fetched", service.getAllReturnRequests()));
    }

    @Operation(summary = "Get return requests by employee")
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ApiResponse.success("Employee return requests fetched",
                service.getReturnRequestsByEmployee(employeeId)));
    }

    @Operation(summary = "Approve return request", description = "Approves a pending return. Marks asset as AVAILABLE. Admin only.")
    @PutMapping("/{returnRequestId}/approve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> approve(@PathVariable Long returnRequestId) {
        return ResponseEntity.ok(ApiResponse.success("Return approved",
                service.approveReturn(returnRequestId)));
    }

    @Operation(summary = "Reject return request", description = "Rejects a pending return. Asset stays allocated. Admin only.")
    @PutMapping("/{returnRequestId}/reject")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> reject(@PathVariable Long returnRequestId) {
        return ResponseEntity.ok(ApiResponse.success("Return rejected",
                service.rejectReturn(returnRequestId)));
    }
}
