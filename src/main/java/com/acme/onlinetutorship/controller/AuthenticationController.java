package com.acme.onlinetutorship.controller;

import com.acme.onlinetutorship.security.payload.request.LoginRequest;
import com.acme.onlinetutorship.security.payload.request.SignUpRequest;
import com.acme.onlinetutorship.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    @Operation(summary = "User Registration", description = "Registration for both teacher and student user", tags = {"Authentication"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return this.authenticationService.registerUser(signUpRequest);
    }

    @PostMapping("/signin")
    @Operation(summary = "User Log in", description = "Log in for teacher, student and admin user. Returns JWT and user info", tags = {"Authentication"})
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return this.authenticationService.authenticateUser(loginRequest);
    }

}