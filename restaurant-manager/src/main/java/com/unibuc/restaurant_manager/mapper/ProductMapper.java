package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.ProductDto;
import com.unibuc.restaurant_manager.model.Product;
import org.springframework.stereotype.Component;

@Component
public abstract class ProductMapper<U extends Product, D extends ProductDto> {

    public void updateEntityFromDto(D dto, U entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
    }

}
