package com.anbit.fashionist.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateAddressRequestDTO {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9/, ]+$", message = "can only contain letters and underscore")
    private String name;

    @NotBlank
    @Size(min =11, max = 12)
    @Pattern(regexp = "^08.+$", message = "must start with 08")
    @Pattern(regexp = "^[0-9]*$", message = "must be numbers")
    private String phoneNumber;

    @NotNull
    @Min(1)
    private Long villageId;

    @NotBlank
    @Size(min = 5, max = 5, message="must be 5 digits")
    @Pattern(regexp = "^[0-9]+$")
    private String postalCode;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9/. ]+$", message = "can only contain letter, slash, point, number, and underscore")
    private String fullAddress;
}
