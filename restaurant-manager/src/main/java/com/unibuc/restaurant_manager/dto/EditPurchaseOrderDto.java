package com.unibuc.restaurant_manager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class EditPurchaseOrderDto {

    @NotEmpty(message = "products cannot be null or empty")
    @Valid
    private List<ContainsDto> products;

}
