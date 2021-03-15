package com.acme.onlinetutorship.service;

import com.acme.onlinetutorship.security.payload.request.LoginRequest;
import com.acme.onlinetutorship.security.payload.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

}
