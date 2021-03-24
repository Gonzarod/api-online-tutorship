package com.acme.onlinetutorship.controller;

import com.acme.onlinetutorship.controller.commons.MessageResponse;
import com.acme.onlinetutorship.controller.constants.ResponseConstants;
import com.acme.onlinetutorship.security.payload.request.LoginRequest;
import com.acme.onlinetutorship.security.payload.request.SignUpRequest;
import com.acme.onlinetutorship.security.payload.response.JwtResponse;
import com.acme.onlinetutorship.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseEntity<MessageResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        MessageResponse response;
        try {
            JwtResponse jwtResponse = this.authenticationService.authenticateUser(loginRequest);
            response = MessageResponse.builder().code(ResponseConstants.SUCCESS_CODE).message("Se autenticó correctamente").data(jwtResponse).build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch(BadCredentialsException e){
            response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message("Las credenciales no son correctas").build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e){
            response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message(e.getMessage()).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}