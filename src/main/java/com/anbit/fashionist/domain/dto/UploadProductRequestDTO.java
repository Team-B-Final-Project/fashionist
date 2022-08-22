package com.anbit.fashionist.domain.dto;

import javax.validation.constraints.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UploadProductRequestDTO {
    
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9-/+()., ?]+$", message = "format is not valid")
    private String name;
    
    @NotBlank
    private String description;
    
    @NotNull
    @Min(1)
    private Float price;
    
    @NotNull
    @Min(1)
    private Integer stock;
    
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "can only contain letters and underscore")
    private String categoryName;
}
