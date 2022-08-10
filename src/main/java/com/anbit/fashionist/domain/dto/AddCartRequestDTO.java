package com.anbit.fashionist.domain.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddCartRequestDTO {
    private Long productId;

    private Integer itemUnit;
}
