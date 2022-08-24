package com.anbit.fashionist.domain.dto;

import java.util.Date;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EditProfileRequestDTO {
    private String firstName;

    private String lastName;

    private String email;

    private Date dateOfBirth;

    private String phoneNumber;
}
