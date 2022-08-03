package com.anbit.fashionist.domain.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignUpRequestDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;
    
    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min =11, max = 12)
    private String phoneNumber;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public void setRole(Set<String> role){
        this.role = role;
    }
}
