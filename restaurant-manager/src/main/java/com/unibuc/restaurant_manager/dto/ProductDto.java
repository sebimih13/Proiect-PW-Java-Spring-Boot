package com.unibuc.restaurant_manager.dto;

import com.unibuc.restaurant_manager.validation.OnCreate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class ProductDto {

    @NotNull(groups = OnCreate.class, message = "name is required and cannot be blank")
    private String name;

    @NotNull(groups = OnCreate.class, message = "description is required and cannot be blank")
    private String description;

    @NotNull(groups = OnCreate.class, message = "price cannot be null")
    @Min(value = 1, message = "price must be at least 1")
    private Integer price;

}
