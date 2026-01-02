package com.unibuc.restaurant_manager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class ProductDto {

    @NotNull(message = "name is required and cannot be blank")
    private String name;

    @NotNull(message = "description is required and cannot be blank")
    private String description;

    @NotNull(message = "price cannot be null")
    @Min(value = 1, message = "price must be at least 1")
    private Integer price;

}
