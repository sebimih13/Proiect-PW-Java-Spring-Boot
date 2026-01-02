package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.CookOnly;
import com.unibuc.restaurant_manager.dto.CookDto;
import com.unibuc.restaurant_manager.dto.DishDto;
import com.unibuc.restaurant_manager.service.CookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/cook")
public class CookController {

    @Autowired
    private CookService cookService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getCook(@RequestParam(value = "id", required = true) Integer cookId) {
        return ResponseEntity.ok(cookService.getUserById(cookId));
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllCooks() {
        return ResponseEntity.ok(cookService.getAllUsers());
    }

    @PutMapping("")
    @ResponseBody
    @CookOnly
    public ResponseEntity<?> updateCook(@Valid @RequestBody CookDto cookDto) {
        return ResponseEntity.ok(cookService.updateLoggedUser(cookDto));
    }

    @GetMapping("/mydishes")
    @ResponseBody
    @CookOnly
    public ResponseEntity<?> getMyDishes() {
        return ResponseEntity.ok(cookService.getMyDishes());
    }

    @PostMapping("/add")
    @ResponseBody
    @CookOnly
    public ResponseEntity<?> addDish(@Valid @RequestBody DishDto dishDto) {
        return ResponseEntity.ok(cookService.addDish(dishDto));
    }

    @PutMapping("/edit")
    @ResponseBody
    @CookOnly
    public ResponseEntity<?> editDish(@RequestParam(value = "id", required = true) Integer dishId, @Valid @RequestBody DishDto dishDto) {
        return ResponseEntity.ok(cookService.updateDish(dishId, dishDto));
    }

    @DeleteMapping("/remove")
    @ResponseBody
    @CookOnly
    public ResponseEntity<?> removeEmployee(@RequestParam(value = "id", required = true) Integer dishId) {
        return ResponseEntity.ok(cookService.removeDish(dishId));
    }

}
