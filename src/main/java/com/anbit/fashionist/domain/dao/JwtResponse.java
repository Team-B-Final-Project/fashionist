package com.anbit.fashionist.domain.dao;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private Integer userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<String> roles;
    public JwtResponse(String accessToken, Integer userId, String firstName, String lastName, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
