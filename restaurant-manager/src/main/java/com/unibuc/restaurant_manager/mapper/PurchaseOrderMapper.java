package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.ContainsResponseDto;
import com.unibuc.restaurant_manager.dto.NewPurchaseOrderDto;
import com.unibuc.restaurant_manager.dto.PurchaseOrderResponseDto;
import com.unibuc.restaurant_manager.model.PurchaseOrder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class PurchaseOrderMapper {

    public PurchaseOrderResponseDto toResponseDto(PurchaseOrder entity) {
        List<ContainsResponseDto> products = entity.getProducts() == null ?
                Collections.emptyList() :
                entity.getProducts().stream()
                        .map(c -> ContainsResponseDto.builder()
                                .productName(c.getProduct().getName())
                                .quantity(c.getQuantity())
                                .build())
                        .collect(Collectors.toList());

        return PurchaseOrderResponseDto.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .date(entity.getDate())
                .time(entity.getTime())
                .restaurantName(entity.getRestaurant().getName())
                .products(products)
                .build();
    }

}
