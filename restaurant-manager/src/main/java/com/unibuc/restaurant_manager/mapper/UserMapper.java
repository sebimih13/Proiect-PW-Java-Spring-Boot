package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.UserDto;
import com.unibuc.restaurant_manager.model.User;
import com.unibuc.restaurant_manager.service.JWTService;
import org.springframework.stereotype.Component;

@Component
public abstract class UserMapper<U extends User, D extends UserDto> {

    public void updateEntityFromDto(D dto, U entity) {
        entity.setUsername(dto.getUsername());
        entity.setPassword(JWTService.encryptPassword(dto.getPassword()));
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
    }

}
