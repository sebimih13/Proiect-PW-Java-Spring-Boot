package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.DishDto;
import com.unibuc.restaurant_manager.model.Dish;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class DishMapper extends ProductMapper<Dish, DishDto> {

    @Override
    public void updateEntityFromDto(DishDto dto, Dish entity) {
        super.updateEntityFromDto(dto, entity);

        Optional.ofNullable(dto.getGrams()).ifPresent(entity::setGrams);
    }

}
