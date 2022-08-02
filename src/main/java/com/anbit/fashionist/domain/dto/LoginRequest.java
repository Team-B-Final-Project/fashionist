package com.anbit.fashionist.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
