package com.acme.onlinetutorship.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class SignUpRequest {
    @NotBlank
    @Size(min = 5, max = 30)
    private String username;


    @NotBlank
    @Size(min = 6, max = 120)
    private String password;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 10, min = 8)
    private String dni;

    @NotBlank
    @Size(max = 12, min = 9)
    private String phone;


    public SignUpRequest(String username, String password, String email, Set<String> role, String name,
                         String lastName,String dni, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;
    }




}