package com.acme.onlinetutorship.service;

import com.acme.onlinetutorship.security.payload.request.LoginRequest;
import com.acme.onlinetutorship.security.payload.request.SignUpRequest;
import com.acme.onlinetutorship.security.payload.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);
    JwtResponse authenticateUser(LoginRequest loginRequest);

}
