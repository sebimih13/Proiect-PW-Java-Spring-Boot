package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.CookDto;
import com.unibuc.restaurant_manager.dto.DishDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.mapper.CookMapper;
import com.unibuc.restaurant_manager.mapper.DishMapper;
import com.unibuc.restaurant_manager.model.*;
import com.unibuc.restaurant_manager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public final class CookService extends UserService<Cook, CookDto> {

    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CookMapper cookMapper;

    @Autowired
    private DishMapper dishMapper;

    @Override
    protected JpaRepository<Cook, Integer> getRepository() {
        return cookRepository;
    }

    @Override
    protected String getUserEntityName() {
        return "Cook";
    }

    @Override
    protected CookMapper getMapper() {
        return cookMapper;
    }

    public List<Dish> getMyDishes() {
        Cook cook = (Cook) jwtService.getUser();
        return cook.getDishes();
    }

    public Dish addDish(DishDto dishDto) {
        Cook cook = (Cook) jwtService.getUser();

        Dish savedDish = dishRepository.save(Dish.builder()
                .name(dishDto.getName())
                .description(dishDto.getDescription())
                .price(dishDto.getPrice())
                .grams(dishDto.getGrams())
                .build());

        cook.getDishes().add(savedDish);
        cookRepository.save(cook);

        return savedDish;
    }

    public Dish updateDish(Integer dishId, DishDto dishDto) {
        Cook cook = (Cook) jwtService.getUser();

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException(String.format("Dish with id '%d' not found", dishId)));

        if (cook.getDishes().stream().noneMatch(d -> d.getId().equals(dish.getId()))) {
            throw new ValidationException("You cannot update a dish you don't own");
        }

        dishMapper.updateEntityFromDto(dishDto, dish);
        return dishRepository.save(dish);
    }

    public Map<String, String> removeDish(Integer dishId) {
        Cook cook = (Cook) jwtService.getUser();

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException(String.format("Dish with id '%d' not found", dishId)));

        if (cook.getDishes().stream().noneMatch(d -> d.getId().equals(dish.getId()))) {
            throw new ValidationException("This dish is not yours");
        }

        cook.getDishes().remove(dish);
        cookRepository.save(cook);

        return Map.of("success", "Dish removed from your list successfully");
    }

}
