package com.anbit.fashionist.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EditCartTotalItemRequestDTO {
    private Long cartId;
    
    private Integer itemUnit;
}
