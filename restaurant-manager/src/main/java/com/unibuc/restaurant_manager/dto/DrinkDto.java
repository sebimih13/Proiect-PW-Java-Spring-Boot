package com.unibuc.restaurant_manager.dto;

import com.unibuc.restaurant_manager.validation.OnCreate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class DrinkDto extends ProductDto {

    @NotNull(groups = OnCreate.class, message = "ml cannot be null")
    @Min(value = 1, message = "ml must be at least 1")
    private Integer ml;

}
