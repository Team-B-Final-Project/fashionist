package com.anbit.fashionist.domain.dto;

import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateTransactionRequestDTO {
    private Map<Long, String> cartShipping;

    @NotNull
    @Min(1)
    private Long sendAddressId;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "can only contain letters and underscore")
    private String paymentMethod;
}
