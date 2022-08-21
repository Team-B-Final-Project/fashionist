package com.anbit.fashionist.domain.dto;

import com.anbit.fashionist.domain.dao.Product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UploadProductRequestDTO {
    
    private String name;
    
    private String description;
    
    private Float price;
    
    private Integer stock;

    private String categoryName;

    public Product convertToEntity() {
        return Product.builder()
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .build();
    }
}
