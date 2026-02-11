package com.example.Event_Manager.service;

import com.example.Event_Manager.dto.LoginRequest;
import com.example.Event_Manager.dto.RegisterRequest;
import com.example.Event_Manager.model.Role;
import com.example.Event_Manager.model.User;
import com.example.Event_Manager.repository.UserRepository;
import com.example.Event_Manager.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //    Register Functionality
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.STUDENT);     // default role
        user.setVerified(false);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

//    Login Functionality
    public String login(LoginRequest request){
        User user= userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("Invalid credentials"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException();
        }

        return jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

    }
}