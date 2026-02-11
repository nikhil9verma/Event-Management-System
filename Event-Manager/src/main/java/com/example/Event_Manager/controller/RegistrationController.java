package com.example.Event_Manager.controller;

import com.example.Event_Manager.dto.MyRegistrationResponse;
import com.example.Event_Manager.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{eventId}")
    // CHANGED: Used @PathVariable instead of @RequestBody to simplify testing
    public ResponseEntity<String> registerForEvent(@PathVariable Long eventId, Authentication authentication) {

        String email = authentication.getName();

        // --- DEBUG LOGS START ---
        System.out.println("\n>>> [STEP 1] Controller Hit: Request received.");
        System.out.println(">>> [STEP 2] User Email: " + email);
        System.out.println(">>> [STEP 3] Event ID: " + eventId);
        // ------------------------

        try {
            System.out.println(">>> [STEP 4] Calling RegistrationService...");
            registrationService.registerForEvent(eventId, email);
            System.out.println(">>> [STEP 5] RegistrationService finished successfully.");

            return ResponseEntity.ok("Successfully registered! (Check Console logs for Email Status)");

        } catch (RuntimeException e) {
            // This catches logic errors like "Event Full" or "Deadline Passed"
            System.err.println(">>> [ERROR] Logic Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Registration Failed: " + e.getMessage());

        } catch (Exception e) {
            // This catches unexpected system crashes
            System.err.println(">>> [ERROR] System Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("System Error: " + e.getMessage());
        }
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MyRegistrationResponse>> getMyRegistrations(Authentication authentication) {
        String email = authentication.getName();
        List<MyRegistrationResponse> response = registrationService.getMyRegistrations(email);
        return ResponseEntity.ok(response);
    }
}