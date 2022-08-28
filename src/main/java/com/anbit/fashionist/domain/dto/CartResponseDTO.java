package com.anbit.fashionist.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDTO {
    private Long id;

    private Long productId;

    private Long userId;

    private Integer itemUnit;

    private Float totalPrice;
}
