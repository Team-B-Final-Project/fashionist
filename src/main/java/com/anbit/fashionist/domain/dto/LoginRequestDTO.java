package com.anbit.fashionist.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequestDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
