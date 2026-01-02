package com.unibuc.restaurant_manager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDto {

    public enum Status {
        PENDING,
        COMPLETED,
        CANCELED
    }

    private Integer customerId;

    @NotNull(message = "restaurantId cannot be null")
    private Integer restaurantId;

    @NotEmpty(message = "products cannot be null or empty")
    @Valid
    private List<ContainsDto> products;

}
