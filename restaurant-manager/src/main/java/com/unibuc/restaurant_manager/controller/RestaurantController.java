package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getManager(@RequestParam(value = "id", required = true) Integer restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllManagers() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("dishes")
    @ResponseBody
    public ResponseEntity<?> getDishesByRestaurantId(@RequestParam(value = "id", required = true) Integer restaurantId) {
        return ResponseEntity.ok(restaurantService.getDishesByRestaurantId(restaurantId));
    }

    @GetMapping("drinks")
    @ResponseBody
    public ResponseEntity<?> getDrinksByRestaurantId(@RequestParam(value = "id", required = true) Integer restaurantId) {
        return ResponseEntity.ok(restaurantService.getDrinksByRestaurantId(restaurantId));
    }

}
