package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.BartenderOnly;
import com.unibuc.restaurant_manager.dto.BartenderDto;
import com.unibuc.restaurant_manager.dto.DrinkDto;
import com.unibuc.restaurant_manager.service.BartenderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/bartender")
public class BartenderController {

    @Autowired
    private BartenderService bartenderService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getBartender(@RequestParam(value = "id", required = true) Integer bartenderId) {
        return ResponseEntity.ok(bartenderService.getUserById(bartenderId));
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllBartenders() {
        return ResponseEntity.ok(bartenderService.getAllUsers());
    }

    @PutMapping("")
    @ResponseBody
    @BartenderOnly
    public ResponseEntity<?> updateBartender(@Valid @RequestBody BartenderDto bartenderDto) {
        return ResponseEntity.ok(bartenderService.updateLoggedUser(bartenderDto));
    }

    @GetMapping("/mydrinks")
    @ResponseBody
    @BartenderOnly
    public ResponseEntity<?> getMyDrinks() {
        return ResponseEntity.ok(bartenderService.getMyDrinks());
    }

    @PostMapping("/add")
    @ResponseBody
    @BartenderOnly
    public ResponseEntity<?> addDrink(@Valid @RequestBody DrinkDto drinkDto) {
        return ResponseEntity.ok(bartenderService.addDrink(drinkDto));
    }

    @PutMapping("/edit")
    @ResponseBody
    @BartenderOnly
    public ResponseEntity<?> editDrink(@RequestParam(value = "id", required = true) Integer drinkId, @Valid @RequestBody DrinkDto drinkDto) {
        return ResponseEntity.ok(bartenderService.updateDrink(drinkId, drinkDto));
    }

    @DeleteMapping("/remove")
    @ResponseBody
    @BartenderOnly
    public ResponseEntity<?> removeEmployee(@RequestParam(value = "id", required = true) Integer drinkId) {
        return ResponseEntity.ok(bartenderService.removeDrink(drinkId));
    }

}
