package com.anbit.fashionist.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EditCartTotalItemRequestDTO {
    @NotNull
    @Min(1)
    private Long cartId;
    
    @NotNull
    @Min(1)
    @Max(10000)
    private Integer itemUnit;
}
