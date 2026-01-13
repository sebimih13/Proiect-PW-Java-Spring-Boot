package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.BartenderDto;
import com.unibuc.restaurant_manager.model.Bartender;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class BartenderMapper extends EmployeeMapper<Bartender, BartenderDto> {

    @Override
    public void updateEntityFromDto(BartenderDto dto, Bartender entity) {
        super.updateEntityFromDto(dto, entity);

        Optional.ofNullable(dto.getSpecialization()).ifPresent(entity::setSpecialization);
    }

}
