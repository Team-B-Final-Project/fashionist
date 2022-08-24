package com.anbit.fashionist.domain.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConfirmOTPRequestDTO {
    @NotBlank
    @Size(max = 50)
    @Email
    private String emailAddress;

    @NotBlank
    private int otp;
}
