package com.anbit.fashionist.domain.dto;


import javax.validation.constraints.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignUpRequestDTO {
    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "[a-zA-Z]+", message = "can only contain letters with no whitespace")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "can only contain letters and underscore")
    private String sex;
    
    @Size(min = 2, max = 20)
    @Pattern(regexp = "[a-zA-Z]+", message = "can only contain letters with no whitespace")
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "(?![_.]).+", message = "can't contain '_' at the beginning")
    @Pattern(regexp = "(?!.*[_.]{2}).+", message = "can't contain double '_'")
    @Pattern(regexp = "[a-zA-Z0-9._]+(?<![.])", message = "can only contain letters, numbers and underscore")
    private String username;
    
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @NotBlank
    @Size(min =11, max = 12)
    @Pattern(regexp = "08.+", message = "must start with 08")
    @Pattern(regexp = "([0-9]*)", message = "must be numbers")
    private String phoneNumber;
    
    @NotBlank
    @Size(min = 6, max = 40)
    @Pattern(regexp = "(?=.*[A-Z].*[A-Z]).+", message = "must at least have 1 uppercase letter")
    @Pattern(regexp = "(?=.*[a-z].*[a-z].*[a-z]).+", message = "must at least have 3 lowercase letters")
    @Pattern(regexp = "(?=.*[0-9].*[0-9]).+", message = "must at least have 2 numbers")
    @Pattern(regexp = "(?=.*[!@#$&*]).+", message = "must at least have 1 special character '!@#$&*'")
    private String password;
}
