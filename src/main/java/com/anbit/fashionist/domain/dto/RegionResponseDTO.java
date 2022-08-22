package com.anbit.fashionist.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegionResponseDTO {
    private String province;

    private String regency;

    private String district;

    private String village;
}
