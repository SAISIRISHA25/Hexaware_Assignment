package hexaware.casestudy.assetmanagement.controller;

import hexaware.casestudy.assetmanagement.dto.request.ServiceRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.ApiResponse;
import hexaware.casestudy.assetmanagement.enums.ServiceStatus;
import hexaware.casestudy.assetmanagement.service.ServiceRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Service Requests", description = "Employees raise service requests; Admins update the status.")
@RestController
@RequestMapping("/api/v1/service-requests")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService service;

    @Operation(summary = "Create service request", description = "Employee raises a service request for an asset (malfunction, repair, etc.).")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody ServiceRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Service request created", service.createServiceRequest(request)));
    }

    @Operation(summary = "Get all service requests", description = "Returns all service requests. Admin only.")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Service requests fetched", service.getAllServiceRequests()));
    }

    @Operation(summary = "Get service requests by employee")
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ApiResponse.success("Employee service requests fetched",
                service.getServiceRequestsByEmployee(employeeId)));
    }

    @Operation(summary = "Update service request status", description = "Admin updates the status of a service request (IN_PROGRESS, COMPLETED, REJECTED). Admin only.")
    @PutMapping("/{requestId}/status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> updateStatus(
            @PathVariable Long requestId,
            @RequestParam ServiceStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Service status updated",
                service.updateStatus(requestId, status)));
    }
}
