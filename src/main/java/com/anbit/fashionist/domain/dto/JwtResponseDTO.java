package com.anbit.fashionist.domain.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponseDTO {
    private String accessToken;
    private String type = "Bearer";
    private String username;
    private List<String> roles;
    public JwtResponseDTO(String accessToken, String username, List<String> roles) {
        this.accessToken = accessToken;
        this.username = username;
        this.roles = roles;
    }
}
