package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.BartenderDto;
import com.unibuc.restaurant_manager.dto.DrinkDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.mapper.BartenderMapper;
import com.unibuc.restaurant_manager.mapper.DrinkMapper;
import com.unibuc.restaurant_manager.model.Bartender;
import com.unibuc.restaurant_manager.model.Drink;
import com.unibuc.restaurant_manager.repository.BartenderRepository;
import com.unibuc.restaurant_manager.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public final class BartenderService extends UserService<Bartender, BartenderDto> {

    @Autowired
    private BartenderRepository bartenderRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private BartenderMapper bartenderMapper;

    @Autowired
    private DrinkMapper drinkMapper;

    @Override
    protected JpaRepository<Bartender, Integer> getRepository() {
        return bartenderRepository;
    }

    @Override
    protected String getUserEntityName() {
        return "Bartender";
    }

    @Override
    protected BartenderMapper getMapper() {
        return bartenderMapper;
    }

    public List<Drink> getMyDrinks() {
        Bartender bartender = (Bartender) jwtService.getUser();
        return bartender.getDrinks();
    }

    public Drink addDrink(DrinkDto drinkDto) {
        Bartender bartender = (Bartender) jwtService.getUser();

        Drink savedDrink = drinkRepository.save(Drink.builder()
                .name(drinkDto.getName())
                .description(drinkDto.getDescription())
                .price(drinkDto.getPrice())
                .ml(drinkDto.getMl())
                .build());

        bartender.getDrinks().add(savedDrink);
        bartenderRepository.save(bartender);

        return savedDrink;
    }

    public Drink updateDrink(Integer drinkId, DrinkDto drinkDto) {
        Bartender bartender = (Bartender) jwtService.getUser();

        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(() -> new NotFoundException(String.format("Drink with id '%d' not found", drinkId)));

        if (bartender.getDrinks().stream().noneMatch(d -> d.getId().equals(drink.getId()))) {
            throw new ValidationException("You cannot update a drink you don't own");
        }

        drinkMapper.updateEntityFromDto(drinkDto, drink);
        return drinkRepository.save(drink);
    }

    public Map<String, String> removeDrink(Integer drinkId) {
        Bartender bartender = (Bartender) jwtService.getUser();

        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(() -> new NotFoundException(String.format("Drink with id '%d' not found", drinkId)));

        if (bartender.getDrinks().stream().noneMatch(d -> d.getId().equals(drink.getId()))) {
            throw new ValidationException("This drink is not yours");
        }

        bartender.getDrinks().remove(drink);
        bartenderRepository.save(bartender);

        return Map.of("success", "Drink removed from your list successfully");
    }

}
