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
    
    private AddressResponseDTO sendAddress;

    private String paymentMethod;

    private String status;

    private String receipt;

    private List<Product> products;

    public void setSendAddress(Address address) {
        AddressResponseDTO responseDTO = new AddressResponseDTO();
        this.sendAddress = responseDTO;
        this.sendAddress.setId(address.getId());
        this.sendAddress.setName(address.getName());
        this.sendAddress.setPhoneNumber(address.getPhoneNumber());
        this.sendAddress.setProvince(address.getProvince());
        this.sendAddress.setCity(address.getCity());
        this.sendAddress.setDistrict(address.getDistrict());
        this.sendAddress.setFullAddress(address.getFullAddress());
    }
}
