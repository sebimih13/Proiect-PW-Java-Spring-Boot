package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.CookDto;
import com.unibuc.restaurant_manager.model.Cook;
import org.springframework.stereotype.Component;

@Component
public final class CookMapper extends EmployeeMapper<Cook, CookDto> {

    @Override
    public void updateEntityFromDto(CookDto dto, Cook entity) {
        super.updateEntityFromDto(dto, entity);

        entity.setSpecialization(dto.getSpecialization());
    }

}
