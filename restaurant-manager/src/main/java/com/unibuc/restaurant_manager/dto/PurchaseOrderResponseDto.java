package com.unibuc.restaurant_manager.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PurchaseOrderResponseDto {

    private Integer id;
    private String status;
    private LocalDate date;
    private LocalTime time;
    private String restaurantName;
    private List<ContainsResponseDto> products;
    private Long totalPrice;

}
