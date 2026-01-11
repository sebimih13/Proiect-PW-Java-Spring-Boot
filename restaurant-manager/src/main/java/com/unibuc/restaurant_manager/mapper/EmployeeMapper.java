package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.EmployeeDto;
import com.unibuc.restaurant_manager.model.Employee;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeMapper<U extends Employee, D extends EmployeeDto> extends UserMapper<U, D> {

    @Override
    public void updateEntityFromDto(D dto, U entity) {
        super.updateEntityFromDto(dto, entity);

        Optional.ofNullable(dto.getBirthDate()).ifPresent(entity::setBirthDate);
        Optional.ofNullable(dto.getCNP()).ifPresent(entity::setCNP);
        Optional.ofNullable(dto.getIDSeries()).ifPresent(entity::setIDSeries);
        Optional.ofNullable(dto.getIDNumber()).ifPresent(entity::setIDNumber);
    }

}
