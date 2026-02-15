package com.example.Event_Manager.controller;

import com.example.Event_Manager.dto.RoleRequestResponse;
import com.example.Event_Manager.service.SuperAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role-requests")
public class RoleRequestController {

    private final SuperAdminService superAdminService;

    public RoleRequestController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }

    // =============================
    // 1️⃣ STUDENT APPLY
    // =============================
    @PostMapping("/apply")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> applyForHost(Authentication authentication) {

        String email = authentication.getName();
        superAdminService.requestHostAccess(email);

        return ResponseEntity.ok("Host access request submitted successfully.");
    }

    // =============================
    // 2️⃣ SUPER_ADMIN VIEW PENDING
    // =============================
    @GetMapping("/pending")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<RoleRequestResponse>> getPendingRequests() {

        return ResponseEntity.ok(
                superAdminService.getAllPendingRequests()
        );
    }

    // =============================
    // 3️⃣ APPROVE
    // =============================
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> approveRequest(@PathVariable Long id) {

        superAdminService.handleRequest(id, true);
        return ResponseEntity.ok("Request approved successfully.");
    }

    // =============================
    // 4️⃣ REJECT
    // =============================
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> rejectRequest(@PathVariable Long id) {

        superAdminService.handleRequest(id, false);
        return ResponseEntity.ok("Request rejected successfully.");
    }
}
