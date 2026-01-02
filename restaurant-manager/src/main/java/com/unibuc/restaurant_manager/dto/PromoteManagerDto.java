package com.unibuc.restaurant_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class PromoteManagerDto {

    @NotNull(message = "employeeID cannot be null")
    private Integer employeeID;

    @NotNull(message = "restaurantId cannot be null")
    private Integer restaurantId;

}
