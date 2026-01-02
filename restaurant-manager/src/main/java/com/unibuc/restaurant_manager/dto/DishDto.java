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

    @NotNull(message = "grams cannot be null")
    @Min(value = 1, message = "grams must be at least 1")
    private Integer grams;

}
