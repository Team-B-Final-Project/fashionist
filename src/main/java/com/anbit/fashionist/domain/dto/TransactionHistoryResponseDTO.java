package com.anbit.fashionist.domain.dto;

import java.util.ArrayList;
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

    private List<ProductResponseDTO> products;

    public void setSendAddress(Address address) {
        AddressResponseDTO responseDTO = new AddressResponseDTO();
        this.sendAddress = responseDTO;
        this.sendAddress.setId(address.getId());
        this.sendAddress.setName(address.getName());
        this.sendAddress.setRegion(
            address.getVillage().getDistrict().getRegency().getProvince(), 
            address.getVillage().getDistrict().getRegency(), 
            address.getVillage().getDistrict(), 
            address.getVillage()
        );
        this.sendAddress.setPhoneNumber(address.getPhoneNumber());
        this.sendAddress.setFullAddress(address.getFullAddress());
    }

    public void setProducts(List<Product> productList) {
        List<ProductResponseDTO> responseDTO = new ArrayList<>();
        this.products = responseDTO;
        productList.forEach(product -> {
            List<String> productPictureUrl = new ArrayList<>();
            product.getPictures().forEach(picture -> {
                productPictureUrl.add(picture.getUrl());
            });
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setProductPictureUrl(productPictureUrl);
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());
            dto.setDescription(product.getDescription());
            this.products.add(dto);
        });
    }
}
