package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.ManagerDto;
import com.unibuc.restaurant_manager.model.Manager;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class ManagerMapper extends EmployeeMapper<Manager, ManagerDto> {

    @Override
    public void updateEntityFromDto(ManagerDto dto, Manager entity) {
        super.updateEntityFromDto(dto, entity);

        Optional.ofNullable(dto.getEducationLevel()).ifPresent(entity::setEducationLevel);
    }

}
