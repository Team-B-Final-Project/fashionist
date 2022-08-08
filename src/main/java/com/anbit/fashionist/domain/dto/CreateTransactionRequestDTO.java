package com.anbit.fashionist.domain.dto;

import java.util.Map;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateTransactionRequestDTO {
    private Long userId;

    private Integer totalItemunits;

    private Map<Long, String> cartShipping;

    private Float totalPrice;

    private Long sendAddressId;

    private String paymentMethod;
}
