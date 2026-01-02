package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.model.Customer;
import org.springframework.stereotype.Component;

@Component
public final class CustomerMapper extends UserMapper<Customer, CustomerDto> {

    @Override
    public void updateEntityFromDto(CustomerDto dto, Customer entity) {
        super.updateEntityFromDto(dto, entity);

        entity.setAddress(dto.getAddress());
    }

}
