package com.acme.onlinetutorship.service.impl;

import com.acme.onlinetutorship.model.ERole;
import com.acme.onlinetutorship.model.Role;
import com.acme.onlinetutorship.model.User;
import com.acme.onlinetutorship.repository.RoleRepository;
import com.acme.onlinetutorship.repository.UserRepository;
import com.acme.onlinetutorship.security.jwt.JwtUtils;
import com.acme.onlinetutorship.security.payload.request.LoginRequest;
import com.acme.onlinetutorship.security.payload.request.SignUpRequest;
import com.acme.onlinetutorship.security.payload.response.JwtResponse;
import com.acme.onlinetutorship.security.payload.response.Message;
import com.acme.onlinetutorship.security.service.UserDetailsImpl;
import com.acme.onlinetutorship.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Message("Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Message("Email is already taken"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), signUpRequest.getName(),
                signUpRequest.getLastName(), signUpRequest.getDni(), signUpRequest.getPhone());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            return ResponseEntity.badRequest().body(new Message("Role must not be null ):"));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "ROLE_STUDENT":
                        Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role student is not found. :("));
                        roles.add(studentRole);

                        break;
                    case "ROLE_TEACHER":
                        Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error: Role teacher is not found. :("));
                        roles.add(teacherRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_NONE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            String roleOne="";
            for(String r: roles){
                roleOne=roleOne.concat(r);
            }


            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roleOne, userDetails.getName(),
                    userDetails.getLastName(), userDetails.getDni(), userDetails.getPhone()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new Message("Bad Credentials"));
        }

    }


}
