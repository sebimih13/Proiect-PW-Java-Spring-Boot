package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.UserDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.mapper.UserMapper;
import com.unibuc.restaurant_manager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class UserService<U extends User, D extends UserDto> {

    @Autowired
    private JWTService jwtService;

    public final List<U> getAllUsers() {
        return getRepository().findAll();
    }

    public final U getUserById(Integer id) {
        Optional<U> user = getRepository().findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("No %s found at id %d", getUserEntityName(), id));
        }

        return user.get();
    }

    public final U updateLoggedUser(D userDto) {
        User currentUser = jwtService.getUser();

        @SuppressWarnings("unchecked")
        U userEntity = (U) currentUser;

        getMapper().updateEntityFromDto(userDto, userEntity);

        return getRepository().save(userEntity);
    }

    protected abstract JpaRepository<U, Integer> getRepository();
    protected abstract String getUserEntityName();
    protected abstract UserMapper<U, D> getMapper();

}
