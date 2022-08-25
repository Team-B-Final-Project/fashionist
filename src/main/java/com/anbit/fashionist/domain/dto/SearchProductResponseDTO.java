package com.anbit.fashionist.domain.dto;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SearchProductResponseDTO {
    private Long id;

    private List<String> productPictureUrl;

    private String name;

    private Float price;
    
    private String city;
}
