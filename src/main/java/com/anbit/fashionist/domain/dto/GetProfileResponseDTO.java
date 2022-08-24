package com.anbit.fashionist.domain.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetProfileResponseDTO {
    private Long id;

    private String profilePictureUrl;

    private String firstName;

    private String lastName;

    private String sex;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dateOfBirth;

    private String username;

    private String email;

    private String phoneNumber;
}
