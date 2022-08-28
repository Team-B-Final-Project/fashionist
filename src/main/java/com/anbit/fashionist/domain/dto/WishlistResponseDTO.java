package com.anbit.fashionist.domain.dto;

import java.util.ArrayList;
import java.util.List;

import com.anbit.fashionist.domain.dao.Product;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponseDTO {

    private Long id;

    private SearchProductResponseDTO product; 

    public void setProduct(Product product){
        List<String> productPictureUrl = new ArrayList<>();
        product.getPictures().forEach(picture -> {
            productPictureUrl.add(picture.getUrl());
        });
        SearchProductResponseDTO responseDTO = new SearchProductResponseDTO();
        this.product = responseDTO;
        this.product.setId(product.getId());
        this.product.setProductPictureUrl(productPictureUrl);
        this.product.setName(product.getName());
        this.product.setPrice(product.getPrice());
        this.product.setCity(product.getStore().getAddress().getVillage().getDistrict().getRegency().getName());
    }

}
