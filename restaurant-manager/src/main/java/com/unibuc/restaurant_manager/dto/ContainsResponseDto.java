package com.unibuc.restaurant_manager.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ContainsResponseDto {

    private String productName;
    private Integer quantity;

}
