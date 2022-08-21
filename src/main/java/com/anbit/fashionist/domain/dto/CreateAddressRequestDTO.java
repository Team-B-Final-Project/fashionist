package com.anbit.fashionist.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateAddressRequestDTO {
    private String name;

    private String phoneNumber;

    private Long villageId;

    private String postalCode;

    private String fullAddress;
}
