package com.anbit.fashionist.domain.dto;

import java.util.List;

import com.anbit.fashionist.domain.dao.Address;
import com.anbit.fashionist.domain.dao.Product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionHistoryResponseDTO {
    private Long id;

    private int totalItemUnit; 

    private Float totalPrice;

    private Float shippingPrice; 
    
    private Address sendAddress;

    private String paymentMethod;

    private String status;

    private String receipt;

    private List<Product> products;
}