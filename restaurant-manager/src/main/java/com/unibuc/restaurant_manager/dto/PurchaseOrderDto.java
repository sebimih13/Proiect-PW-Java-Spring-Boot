package com.unibuc.restaurant_manager.dto;

import com.unibuc.restaurant_manager.model.PurchaseOrder;
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
public final class PurchaseOrderDto {

    private PurchaseOrder.Status status;
    private Integer customerId;

    @NotNull(message = "restaurantId cannot be null")
    private Integer restaurantId;

    @NotEmpty(message = "products cannot be null or empty")
    @Valid
    private List<ContainsDto> products;

}
