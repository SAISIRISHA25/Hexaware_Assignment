package hexaware.casestudy.assetmanagement.controller;

import hexaware.casestudy.assetmanagement.dto.request.AuditRequestDto;
import hexaware.casestudy.assetmanagement.dto.response.ApiResponse;
import hexaware.casestudy.assetmanagement.service.AuditRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Audit Requests", description = "Admins send audit requests to employees; employees view their pending audits.")
@RestController
@RequestMapping("/api/v1/audit-requests")
@RequiredArgsConstructor
public class AuditRequestController {

    private final AuditRequestService service;

    @Operation(summary = "Create audit request", description = "Admin sends an audit request to an employee for a specific asset. Admin only.")
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody AuditRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Audit request created", service.createAuditRequest(request)));
    }

    @Operation(summary = "Get all audit requests", description = "Returns all audit requests with their status. Admin only.")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Audit requests fetched", service.getAllAuditRequests()));
    }

    @Operation(summary = "Get audit requests for an employee", description = "Returns pending/completed audit requests assigned to a specific employee.")
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ApiResponse.success("Employee audit requests fetched",
                service.getAuditRequestsByEmployee(employeeId)));
    }

    @Operation(summary = "Get audit requests sent by admin")
    @GetMapping("/admin/{adminId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getByAdmin(@PathVariable Long adminId) {
        return ResponseEntity.ok(ApiResponse.success("Admin audit requests fetched",
                service.getAuditRequestsByAdmin(adminId)));
    }
}
