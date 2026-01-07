package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.DishDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.mapper.CookMapper;
import com.unibuc.restaurant_manager.mapper.DishMapper;
import com.unibuc.restaurant_manager.model.Cook;
import com.unibuc.restaurant_manager.model.Dish;
import com.unibuc.restaurant_manager.repository.CookRepository;
import com.unibuc.restaurant_manager.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CookServiceTest {

    @Mock
    private CookRepository cookRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private CookMapper cookMapper;

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private CookService cookService;

    private Cook cook;
    private Dish dish;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cook = Cook.builder()
                .id(30)
                .username("cook")
                .password(JWTService.encryptPassword("cook123-PASSWORD"))
                .firstName("Cook-FirstName")
                .lastName("Cook-LastName")
                .email("cook@mail.com")
                .phoneNumber("0712345678")
                .dishes(new ArrayList<>())
                .build();

        dish = Dish.builder()
                .id(1)
                .name("Pasta")
                .description("Italian pasta")
                .price(25)
                .grams(300)
                .build();
    }

    @Test
    void getMyDishes_ShouldReturnList() {
        cook.getDishes().add(dish);
        when(jwtService.getUser()).thenReturn(cook);

        List<Dish> dishes = cookService.getMyDishes();

        assertNotNull(dishes);
        assertEquals(1, dishes.size());
        assertEquals("Pasta", dishes.get(0).getName());
    }

    @Test
    void addDish_ShouldAddAndReturnDish() {
        DishDto dto = DishDto.builder()
                .name("Pasta")
                .description("Italian pasta")
                .price(25)
                .grams(300)
                .build();

        when(jwtService.getUser()).thenReturn(cook);
        when(dishRepository.save(any(Dish.class))).thenReturn(dish);
        when(cookRepository.save(cook)).thenReturn(cook);

        Dish result = cookService.addDish(dto);

        assertNotNull(result);
        assertEquals("Pasta", result.getName());
        assertTrue(cook.getDishes().contains(result));

        verify(dishRepository, times(1)).save(any(Dish.class));
        verify(cookRepository, times(1)).save(cook);
    }

    @Test
    void updateDish_WhenExistsAndOwned_ShouldUpdate() {
        DishDto dto = DishDto.builder()
                .name("Updated Pasta")
                .build();

        cook.getDishes().add(dish);

        when(jwtService.getUser()).thenReturn(cook);
        when(dishRepository.findById(1)).thenReturn(Optional.of(dish));
        when(dishRepository.save(dish)).thenReturn(dish);

        Dish result = cookService.updateDish(1, dto);

        assertNotNull(result);
        verify(dishMapper, times(1)).updateEntityFromDto(dto, dish);
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void updateDish_WhenNotOwned_ShouldThrowValidationException() {
        DishDto dto = new DishDto();

        when(jwtService.getUser()).thenReturn(cook);
        when(dishRepository.findById(1)).thenReturn(Optional.of(dish));

        ValidationException ex = assertThrows(ValidationException.class, () -> cookService.updateDish(1, dto));
        assertEquals("You cannot update a dish you don't own", ex.getMessage());
    }

    @Test
    void updateDish_WhenNotFound_ShouldThrowNotFoundException() {
        DishDto dto = new DishDto();

        when(dishRepository.findById(1)).thenReturn(Optional.empty());
        when(jwtService.getUser()).thenReturn(cook);

        NotFoundException ex = assertThrows(NotFoundException.class, () -> cookService.updateDish(1, dto));
        assertEquals("Dish with id '1' not found", ex.getMessage());
    }

    @Test
    void removeDish_WhenOwned_ShouldRemove() {
        cook.getDishes().add(dish);

        when(jwtService.getUser()).thenReturn(cook);
        when(dishRepository.findById(1)).thenReturn(Optional.of(dish));
        when(cookRepository.save(cook)).thenReturn(cook);

        Map<String, String> result = cookService.removeDish(1);

        assertEquals("Dish removed from your list successfully", result.get("success"));
        assertFalse(cook.getDishes().contains(dish));

        verify(cookRepository, times(1)).save(cook);
    }

    @Test
    void removeDish_WhenNotOwned_ShouldThrowValidationException() {
        when(jwtService.getUser()).thenReturn(cook);
        when(dishRepository.findById(1)).thenReturn(Optional.of(dish));

        ValidationException ex = assertThrows(ValidationException.class, () -> cookService.removeDish(1));
        assertEquals("You can only remove your own dishes", ex.getMessage());
    }

    @Test
    void removeDish_WhenNotFound_ShouldThrowNotFoundException() {
        when(dishRepository.findById(1)).thenReturn(Optional.empty());
        when(jwtService.getUser()).thenReturn(cook);

        NotFoundException ex = assertThrows(NotFoundException.class, () -> cookService.removeDish(1));
        assertEquals("Dish with id '1' not found", ex.getMessage());
    }

}
