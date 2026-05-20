package hexaware.casestudy.assetmanagement.controller;

import hexaware.casestudy.assetmanagement.dto.request.AuditResponseDto;
import hexaware.casestudy.assetmanagement.dto.response.ApiResponse;
import hexaware.casestudy.assetmanagement.service.AuditResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Audit Responses", description = "Employees respond to audit requests sent by the admin.")
@RestController
@RequestMapping("/api/v1/audit-responses")
@RequiredArgsConstructor
public class AuditResponseController {

    private final AuditResponseService service;

    @Operation(summary = "Submit audit response", description = "Employee verifies or rejects the asset condition in response to an admin audit request.")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> submit(@Valid @RequestBody AuditResponseDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Audit response submitted", service.submitAuditResponse(request)));
    }

    @Operation(summary = "Get all audit responses", description = "Returns all submitted audit responses. Admin only.")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Audit responses fetched", service.getAllAuditResponses()));
    }
}
