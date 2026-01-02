package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.BartenderDto;
import com.unibuc.restaurant_manager.model.Bartender;
import org.springframework.stereotype.Component;

@Component
public final class BartenderMapper extends EmployeeMapper<Bartender, BartenderDto> {

    @Override
    public void updateEntityFromDto(BartenderDto dto, Bartender entity) {
        super.updateEntityFromDto(dto, entity);

        entity.setSpecialization(dto.getSpecialization());
    }

}
