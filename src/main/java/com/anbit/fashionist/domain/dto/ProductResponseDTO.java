package com.anbit.fashionist.domain.dto;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponseDTO {
    private Long id;

    private String name;

    private List<String> productPictureUrl;

    private String description;

    private Float price;

    private Integer stock;

}
