package com.anbit.fashionist.domain.dto;

import com.anbit.fashionist.domain.dao.Review;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewRequestDTO {

    @NotBlank
    private String comment;

    @NotBlank
    private float rating;

    @NotBlank
    private Long productId;

    @NotBlank
    private Long userId;


}
