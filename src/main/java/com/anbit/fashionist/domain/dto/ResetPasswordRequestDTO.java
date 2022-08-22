package com.anbit.fashionist.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResetPasswordRequestDTO {
    @NotBlank
    @Size(min = 6, max = 40)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 40)
    private String confirmPassword;
}
