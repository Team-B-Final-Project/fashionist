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
    @NotBlank
    @Pattern(regexp = "[1-9]")
    private Long productId;

    @Positive
    @Pattern(regexp = "[1-9]")
    @Max(1000)
    @NotNull
    private Integer itemUnit;
}
