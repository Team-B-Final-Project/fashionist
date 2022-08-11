package com.anbit.fashionist.domain.dto;

import java.util.List;

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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class Address {
        private Long id;
    
        private String name;
    
        private String phoneNumber;
    
        private String province;
    
        private String city;
        
        private String district;
    
        private String fullAddress;
    }

    public void setSendAddress(com.anbit.fashionist.domain.dao.Address address) {
        Address a = new Address();
        this.sendAddress = a;
        this.sendAddress.setId(address.getId());
        this.sendAddress.setName(address.getName());
    }
}
