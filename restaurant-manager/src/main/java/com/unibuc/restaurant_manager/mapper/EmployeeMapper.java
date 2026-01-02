package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.EmployeeDto;
import com.unibuc.restaurant_manager.model.Employee;
import org.springframework.stereotype.Component;

@Component
public abstract class EmployeeMapper<U extends Employee, D extends EmployeeDto> extends UserMapper<U, D> {

    @Override
    public void updateEntityFromDto(D dto, U entity) {
        super.updateEntityFromDto(dto, entity);

        entity.setBirthDate(dto.getBirthDate());
        entity.setCNP(dto.getCNP());
        entity.setIDSeries(dto.getIDSeries());
        entity.setIDNumber(dto.getIDNumber());
    }

}
