package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.ManagerDto;
import com.unibuc.restaurant_manager.model.Manager;
import org.springframework.stereotype.Component;

@Component
public final class ManagerMapper extends EmployeeMapper<Manager, ManagerDto> {

    @Override
    public void updateEntityFromDto(ManagerDto dto, Manager entity) {
        super.updateEntityFromDto(dto, entity);

        entity.setEducationLevel(dto.getEducationLevel());
    }

}
