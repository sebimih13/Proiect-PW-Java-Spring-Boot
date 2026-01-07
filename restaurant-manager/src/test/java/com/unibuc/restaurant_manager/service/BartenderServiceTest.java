package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.DrinkDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.mapper.BartenderMapper;
import com.unibuc.restaurant_manager.mapper.DrinkMapper;
import com.unibuc.restaurant_manager.model.Bartender;
import com.unibuc.restaurant_manager.model.Drink;
import com.unibuc.restaurant_manager.repository.BartenderRepository;
import com.unibuc.restaurant_manager.repository.DrinkRepository;
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
public class BartenderServiceTest {

    @Mock
    private BartenderRepository bartenderRepository;

    @Mock
    private DrinkRepository drinkRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private BartenderMapper bartenderMapper;

    @Mock
    private DrinkMapper drinkMapper;

    @InjectMocks
    private BartenderService bartenderService;

    private Bartender bartender;
    private Drink drink;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bartender = Bartender.builder()
                .id(20)
                .username("bartender")
                .password(JWTService.encryptPassword("bartender123-PASSWORD"))
                .firstName("Bartender-FirstName")
                .lastName("Bartender-LastName")
                .email("bartender@mail.com")
                .phoneNumber("0712345678")
                .drinks(new ArrayList<>())
                .build();

        drink = Drink.builder()
                .id(1)
                .name("Mojito")
                .description("Minty cocktail")
                .price(20)
                .ml(300)
                .build();
    }

    @Test
    void getMyDrinks_ShouldReturnList() {
        bartender.getDrinks().add(drink);
        when(jwtService.getUser()).thenReturn(bartender);

        List<Drink> drinks = bartenderService.getMyDrinks();

        assertNotNull(drinks);
        assertEquals(1, drinks.size());
        assertEquals("Mojito", drinks.get(0).getName());
    }

    @Test
    void addDrink_ShouldAddAndReturnDrink() {
        DrinkDto dto = DrinkDto.builder()
                .name("Mojito")
                .description("Minty cocktail")
                .price(20)
                .ml(300)
                .build();

        when(jwtService.getUser()).thenReturn(bartender);
        when(drinkRepository.save(any(Drink.class))).thenReturn(drink);
        when(bartenderRepository.save(bartender)).thenReturn(bartender);

        Drink result = bartenderService.addDrink(dto);

        assertNotNull(result);
        assertEquals("Mojito", result.getName());
        assertTrue(bartender.getDrinks().contains(result));

        verify(drinkRepository, times(1)).save(any(Drink.class));
        verify(bartenderRepository, times(1)).save(bartender);
    }

    @Test
    void updateDrink_WhenExistsAndOwned_ShouldUpdate() {
        DrinkDto dto = DrinkDto.builder()
                .name("Updated Mojito")
                .build();

        bartender.getDrinks().add(drink);

        when(jwtService.getUser()).thenReturn(bartender);
        when(drinkRepository.findById(1)).thenReturn(Optional.of(drink));
        when(drinkRepository.save(drink)).thenReturn(drink);

        Drink result = bartenderService.updateDrink(1, dto);

        assertNotNull(result);

        verify(drinkMapper, times(1)).updateEntityFromDto(dto, drink);
        verify(drinkRepository, times(1)).save(drink);
    }

    @Test
    void updateDrink_WhenNotOwned_ShouldThrowValidationException() {
        DrinkDto dto = new DrinkDto();

        when(jwtService.getUser()).thenReturn(bartender);
        when(drinkRepository.findById(1)).thenReturn(Optional.of(drink));

        ValidationException ex = assertThrows(ValidationException.class, () -> bartenderService.updateDrink(1, dto));
        assertEquals("You cannot update a drink you don't own", ex.getMessage());
    }

    @Test
    void updateDrink_WhenNotFound_ShouldThrowNotFoundException() {
        DrinkDto dto = new DrinkDto();

        when(drinkRepository.findById(1)).thenReturn(Optional.empty());
        when(jwtService.getUser()).thenReturn(bartender);

        NotFoundException ex = assertThrows(NotFoundException.class, () -> bartenderService.updateDrink(1, dto));
        assertEquals("Drink with id '1' not found", ex.getMessage());
    }

    @Test
    void removeDrink_WhenOwned_ShouldRemove() {
        bartender.getDrinks().add(drink);

        when(jwtService.getUser()).thenReturn(bartender);
        when(drinkRepository.findById(1)).thenReturn(Optional.of(drink));
        when(bartenderRepository.save(bartender)).thenReturn(bartender);

        Map<String, String> result = bartenderService.removeDrink(1);

        assertEquals("Drink removed from your list successfully", result.get("success"));
        assertFalse(bartender.getDrinks().contains(drink));

        verify(bartenderRepository, times(1)).save(bartender);
    }

    @Test
    void removeDrink_WhenNotOwned_ShouldThrowValidationException() {
        when(jwtService.getUser()).thenReturn(bartender);
        when(drinkRepository.findById(1)).thenReturn(Optional.of(drink));

        ValidationException ex = assertThrows(ValidationException.class, () -> bartenderService.removeDrink(1));
        assertEquals("This drink is not yours", ex.getMessage());
    }

    @Test
    void removeDrink_WhenNotFound_ShouldThrowNotFoundException() {
        when(drinkRepository.findById(1)).thenReturn(Optional.empty());
        when(jwtService.getUser()).thenReturn(bartender);

        NotFoundException ex = assertThrows(NotFoundException.class, () -> bartenderService.removeDrink(1));
        assertEquals("Drink with id '1' not found", ex.getMessage());
    }

}
