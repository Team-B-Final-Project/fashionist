package com.anbit.fashionist.domain.dto;


import com.anbit.fashionist.domain.dao.Cart;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartRequestDTO {
    private Long id;

    private Integer itemUnit;

    private Float totalPrice;

    public Cart convertToEntity(){
        return Cart.builder()
                .id(this.id)
                .itemUnit(this.itemUnit)
                .totalPrice(this.totalPrice)
                .build();
    }
}
