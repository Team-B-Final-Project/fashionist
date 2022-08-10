package com.anbit.fashionist.domain.dto;

import java.util.Map;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateTransactionRequestDTO {
    private Map<Long, String> cartShipping;

    private Long sendAddressId;

    private String paymentMethod;
}
