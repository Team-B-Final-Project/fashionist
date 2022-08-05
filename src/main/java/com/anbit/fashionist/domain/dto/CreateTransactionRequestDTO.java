package com.anbit.fashionist.domain.dto;

import java.util.Map;

import com.anbit.fashionist.domain.dao.Shipping;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateTransactionRequestDTO {
    private Long userId;

    private Integer totalItemunits;

    private Map<Long, Shipping> cartShipping;

    private Float totalPrice;

    private Long sendAddressId;

    private String paymentMethod;
}
