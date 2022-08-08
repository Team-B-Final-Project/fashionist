package com.anbit.fashionist.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponseDTO {
    private Long productId;

    private String name;

    private String description;

    private Float price;

    private Integer stock;

//    private String categoryId;
}