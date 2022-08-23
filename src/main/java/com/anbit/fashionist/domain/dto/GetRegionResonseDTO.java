package com.anbit.fashionist.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetRegionResonseDTO {
    private Long id;

    private Integer parentId;

    private String name;
}
