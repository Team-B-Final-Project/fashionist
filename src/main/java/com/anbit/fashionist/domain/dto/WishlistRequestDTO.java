package com.anbit.fashionist.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistRequestDTO {
    private Long userId;
    private Long productId;
/*
    public Wishlist convertToEntity(){
        return Wishlist.builder()
            .user(this.userId)
            .product(this.productId)
            .build();

 */

}
