package com.anbit.fashionist.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetCurrentUserAddressesResponseDTO {
    private Long id;

    private String name;

    private String phoneNumber;

    private String province;

    private String city;
    
    private String district;

    private String fullAddress;
}
