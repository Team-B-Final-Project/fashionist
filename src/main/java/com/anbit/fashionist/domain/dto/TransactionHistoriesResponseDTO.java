package com.anbit.fashionist.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionHistoriesResponseDTO {
    private String productName;

    private Float productPrice;
    
    private Integer totalItems;

    private Float totalPricePerItem;
}
