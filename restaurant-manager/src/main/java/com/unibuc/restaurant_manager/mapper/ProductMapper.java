package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.ProductDto;
import com.unibuc.restaurant_manager.model.Product;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public abstract class ProductMapper<U extends Product, D extends ProductDto> {

    public void updateEntityFromDto(D dto, U entity) {        
        Optional.ofNullable(dto.getName()).ifPresent(entity::setName);
        Optional.ofNullable(dto.getDescription()).ifPresent(entity::setDescription);
        Optional.ofNullable(dto.getPrice()).ifPresent(entity::setPrice);
    }

}
