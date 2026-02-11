package com.example.Event_Manager.controller;

import com.example.Event_Manager.dto.AuthResponse;
import com.example.Event_Manager.dto.LoginRequest;
import com.example.Event_Manager.dto.RegisterRequest;
import com.example.Event_Manager.dto.RegisterResponse;
import com.example.Event_Manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        userService.register(request);
        return new ResponseEntity<>(
          new RegisterResponse("User registered successfully"), HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        System.out.println(" LOGIN CONTROLLER HIT 🔥");
        String token=userService.login(request);
        return ResponseEntity.ok(
                new AuthResponse(token,"Login Successful")
        );
    }

}
