package com.anbit.fashionist.domain.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.anbit.fashionist.util.RandomString;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTransactionResponseDTO {
    @Autowired
    RandomString randomString;

    private Float totalPrice;

    private String virtualAccount = randomString.generateVirtualAccount(16);

    public CreateTransactionResponseDTO(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
