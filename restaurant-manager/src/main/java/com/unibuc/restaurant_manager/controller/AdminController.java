package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.AdminOnly;
import com.unibuc.restaurant_manager.dto.AssignManagerDto;
import com.unibuc.restaurant_manager.dto.RestaurantDto;
import com.unibuc.restaurant_manager.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/manager")
    @ResponseBody
    @AdminOnly
    public ResponseEntity<?> addManager(@Valid @RequestBody AssignManagerDto assignManagerDto) {
        return ResponseEntity.ok(adminService.addManager(assignManagerDto));
    }

    @PostMapping("/restaurant")
    @ResponseBody
    @AdminOnly
    public ResponseEntity<?> addRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(adminService.addRestaurant(restaurantDto));
    }

}
