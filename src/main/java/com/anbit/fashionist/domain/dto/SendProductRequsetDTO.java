package com.anbit.fashionist.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SendProductRequsetDTO {
    @NotBlank
    @Size(min = 10, max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9-]*$", message="must only contain alphanumeric character and slash")
    private String receipt;
}
