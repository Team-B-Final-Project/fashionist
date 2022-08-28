package com.anbit.fashionist.domain.dto;


import javax.validation.constraints.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddCartRequestDTO {
    @NotNull
    @Min(1)
    private Long productId;

    @Min(1)
    @Max(100)
    @NotNull
    private Integer itemUnit;
}
