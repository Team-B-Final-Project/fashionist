package com.anbit.fashionist.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTransactionResponseDTO {
    private Float totalPrice;

    private String virtualAccount;
}
