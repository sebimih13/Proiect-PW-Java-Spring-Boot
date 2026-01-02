package com.unibuc.restaurant_manager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class DishDto extends ProductDto {

    @NotNull(message = "price cannot be null")
    @Min(value = 1, message = "price must be at least 1")
    private Integer grams;

}
