package com.anbit.fashionist.domain.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistRequestDTO {

    @NotNull
    @Min(1)
    @Pattern(regexp = "^[0-9]+$")
    private Long productId;
}
