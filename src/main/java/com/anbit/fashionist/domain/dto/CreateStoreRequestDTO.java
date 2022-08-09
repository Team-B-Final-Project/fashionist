package com.anbit.fashionist.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateStoreRequestDTO {
    private String storeName;

    private Long addressId;
}
