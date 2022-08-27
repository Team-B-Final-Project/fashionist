package com.anbit.fashionist.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "(?=.*[A-Z].*[A-Z]).+", message = "must at least have 1 uppercase letter")
    @Pattern(regexp = "(?=.*[a-z].*[a-z].*[a-z]).+", message = "must at least have 3 lowercase letters")
    @Pattern(regexp = "(?=.*[0-9].*[0-9]).+", message = "must at least have 2 numbers")
    @Pattern(regexp = "(?=.*[!@#$&*]).+", message = "must at least have 1 special character '!@#$&*'")
    private String newPassword;

}

