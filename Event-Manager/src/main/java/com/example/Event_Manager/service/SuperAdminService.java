package com.example.Event_Manager.service;

import com.example.Event_Manager.dto.RoleRequestResponse;
import com.example.Event_Manager.model.Role;
import com.example.Event_Manager.model.RoleRequest;
import com.example.Event_Manager.model.User;
import com.example.Event_Manager.repository.RoleRequestRepository;
import com.example.Event_Manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuperAdminService {

    @Autowired
    private RoleRequestRepository roleRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService; // <--- NEW INJECTION

    // 1. User asks to become HOST
    public void requestHostAccess(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (roleRequestRepository.existsByUser(user)) {
            throw new RuntimeException("You already have a pending request.");
        }

        RoleRequest request = new RoleRequest();
        request.setUser(user);
        roleRequestRepository.save(request);
    }

    // 2. Super Admin sees the list (Returning DTOs for safety)
    public List<RoleRequestResponse> getAllPendingRequests() {
        return roleRequestRepository.findAll().stream()
                .map(req -> new RoleRequestResponse(
                        req.getId(),
                        req.getUser().getName(),
                        req.getUser().getEmail(),
                        req.getRequestedAt()
                ))
                .collect(Collectors.toList());
    }

    // 3. Super Admin Acts
    @Transactional
    public void handleRequest(Long requestId, boolean isApproved) {
        RoleRequest request = roleRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User user = request.getUser();

        if (isApproved) {
            // A. Upgrade Role
            user.setRole(Role.HOST);
            userRepository.save(user);

            // B. Send Email Notification
            emailService.sendHostUpgradeEmail(user.getEmail(), user.getName());
        }

        // C. Clean up dashboard
        roleRequestRepository.delete(request);
    }
}