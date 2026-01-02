package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.DrinkDto;
import com.unibuc.restaurant_manager.model.Drink;
import org.springframework.stereotype.Component;

@Component
public final class DrinkMapper extends ProductMapper<Drink, DrinkDto> {

    @Override
    public void updateEntityFromDto(DrinkDto dto, Drink entity) {
        super.updateEntityFromDto(dto, entity);

        entity.setMl(dto.getMl());
    }

}
