package com.unibuc.restaurant_manager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ContainsDto {

    @NotNull(message = "productId cannot be null")
    private Integer productId;

    @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be at least 1")
    private Integer quantity;

}
