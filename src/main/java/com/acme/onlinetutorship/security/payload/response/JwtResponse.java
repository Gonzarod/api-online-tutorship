package com.acme.onlinetutorship.security.payload.response;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    private Long id;
    private String username;
    private String email;
    ///private List<String> roles;
    private String roles;
    private String name;
    private String lastName;
    private String dni;
    private String phone;


    //List<String>
    public JwtResponse(String accessToken, Long id, String username, String email, String roles, String name,
                      String lastName, String dni, String phone){
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;

    }
}
