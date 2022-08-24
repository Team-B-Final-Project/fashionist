package com.anbit.fashionist.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateStoreRequestDTO {
    @NotBlank
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9.-/ ]+$", message = "can only contain letter, number, slash, dash and underscore")
    private String storeName;

    @NotNull
    @Min(1)
    private Long addressId;
}
