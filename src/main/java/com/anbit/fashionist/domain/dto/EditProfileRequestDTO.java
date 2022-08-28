package com.anbit.fashionist.domain.dto;

import java.util.Date;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EditProfileRequestDTO {
    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "[a-zA-Z]+", message = "can only contain letters with no whitespace")
    private String firstName;

    @Size(min = 2, max = 20)
    @Pattern(regexp = "[a-zA-Z]+", message = "can only contain letters with no whitespace")
    private String lastName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @NotBlank
    @Size(min =11, max = 12)
    @Pattern(regexp = "08.+", message = "must start with 08")
    @Pattern(regexp = "([0-9]*)", message = "must be numbers")
    private String phoneNumber;
}
