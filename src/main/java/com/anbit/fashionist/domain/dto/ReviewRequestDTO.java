package com.anbit.fashionist.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewRequestDTO {

    @NotBlank
    private String comment;

    @NotNull
    private double rating;

    @NotNull
    private Long transactionId;

}
