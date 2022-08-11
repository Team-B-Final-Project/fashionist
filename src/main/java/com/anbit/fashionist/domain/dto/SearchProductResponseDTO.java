package com.anbit.fashionist.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SearchProductResponseDTO {
    private Long id;
    private String name;
    private Float price;
    private String city;
}
