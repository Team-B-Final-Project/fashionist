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
    @Pattern(regexp = "^[0-9]+$")
    private Long productId;

    @Pattern(regexp = "^[0-9]+$")
    @Min(1)
    @Max(100)
    @NotNull
    private Integer itemUnit;
}
