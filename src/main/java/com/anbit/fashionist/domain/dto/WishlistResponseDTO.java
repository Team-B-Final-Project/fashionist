package com.anbit.fashionist.domain.dto;

import com.anbit.fashionist.domain.dao.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class WishlistResponseDTO {

    private Long id;

    private Long productId;

//    @Override
//    public String toString(){
//        return "ProductResponseDTO{" +
//                "id=" + id +
//                "product=" + product +
//                '}';
//    }
}
