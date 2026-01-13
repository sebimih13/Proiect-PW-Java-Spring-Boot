package com.unibuc.restaurant_manager.mapper;

import com.unibuc.restaurant_manager.dto.UserDto;
import com.unibuc.restaurant_manager.model.User;
import com.unibuc.restaurant_manager.service.JWTService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public abstract class UserMapper<U extends User, D extends UserDto> {

    public void updateEntityFromDto(D dto, U entity) {
        Optional.ofNullable(dto.getUsername()).ifPresent(entity::setUsername);
        Optional.ofNullable(JWTService.encryptPassword(dto.getPassword())).ifPresent(entity::setPassword);
        Optional.ofNullable(dto.getFirstName()).ifPresent(entity::setFirstName);
        Optional.ofNullable(dto.getLastName()).ifPresent(entity::setLastName);
        Optional.ofNullable(dto.getEmail()).ifPresent(entity::setEmail);
        Optional.ofNullable(dto.getPhoneNumber()).ifPresent(entity::setPhoneNumber);
    }

}
