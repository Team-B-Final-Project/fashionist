package com.anbit.fashionist.domain.dto;

import com.anbit.fashionist.domain.dao.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class WishlistResponseDTO {

    private Long id;

    private Product product;


    @Override
    public String toString(){
        return "ProductResponseDTO{" +
                "id=" + id +
                "product=" + product +
                '}';
    }
}
