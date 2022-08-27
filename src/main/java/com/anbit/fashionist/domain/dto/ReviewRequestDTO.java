package com.anbit.fashionist.domain.dto;

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
    private double rating;

    @NotBlank
    private Long transactionId;

}
