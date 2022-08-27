package com.anbit.fashionist.domain.dto;

import java.util.List;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class WishlistResponseDTO {

    private Long id;

    private Long productId;
    
    private List<String> productPictureUrl;

    private String name;

    private Float price;
    
    private String city;

}
