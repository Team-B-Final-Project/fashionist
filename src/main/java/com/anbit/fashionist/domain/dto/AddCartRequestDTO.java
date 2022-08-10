package com.anbit.fashionist.domain.dto;


import javax.validation.constraints.Size;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddCartRequestDTO {
    private Long productId;

    @Size(min = 1)
    private Integer itemUnit;
}
