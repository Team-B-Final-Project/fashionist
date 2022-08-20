package com.anbit.fashionist.domain.dto;

import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dao.Wishlist;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistRequestDTO {
    private User user;
    private Product product;

    public Wishlist convertToEntity(){
        return Wishlist.builder()
            .user(this.user)
            .product(this.product)
            .build();
    }
}
