package com.anbit.fashionist.domain.dto;

import com.anbit.fashionist.domain.dao.District;
import com.anbit.fashionist.domain.dao.Province;
import com.anbit.fashionist.domain.dao.Regency;
import com.anbit.fashionist.domain.dao.Village;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponseDTO {
    private Long id;

    private String name;

    private String phoneNumber;

    private RegionResponseDTO region;

    private String postalCode;

    private String fullAddress;

    public void setRegion(Province province, Regency regency, District district, Village village) {
        RegionResponseDTO responseDTO = new RegionResponseDTO();
        this.region = responseDTO;
        this.region.setProvince(province.getName());
        this.region.setRegency(regency.getName());
        this.region.setDistrict(district.getName());
        this.region.setVillage(village.getName());
    }
}
