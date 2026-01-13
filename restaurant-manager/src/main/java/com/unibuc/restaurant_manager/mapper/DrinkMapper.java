package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.DrinkDto;
import com.unibuc.restaurant_manager.model.Drink;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class DrinkMapper extends ProductMapper<Drink, DrinkDto> {

    @Override
    public void updateEntityFromDto(DrinkDto dto, Drink entity) {
        super.updateEntityFromDto(dto, entity);

        Optional.ofNullable(dto.getMl()).ifPresent(entity::setMl);
    }

}
