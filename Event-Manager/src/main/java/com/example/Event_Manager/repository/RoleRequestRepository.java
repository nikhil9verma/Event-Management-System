package com.example.Event_Manager.repository;

import com.example.Event_Manager.model.RoleRequest;
import com.example.Event_Manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRequestRepository extends JpaRepository<RoleRequest, Long> {
    // Check if this specific user already has a request in the queue
    boolean existsByUser(User user);
}