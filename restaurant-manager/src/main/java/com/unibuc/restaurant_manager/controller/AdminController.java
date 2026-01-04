package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.AdminOnly;
import com.unibuc.restaurant_manager.dto.AssignManagerDto;
import com.unibuc.restaurant_manager.dto.RestaurantDto;
import com.unibuc.restaurant_manager.model.Manager;
import com.unibuc.restaurant_manager.model.Restaurant;
import com.unibuc.restaurant_manager.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Add a new manager", description = "Assigns a manager to a restaurant. Admin-only operation.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Manager added successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Manager.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"Manager with id 3 already exists\" }"),
                                    @ExampleObject(value = "{ \"error\": \"Restaurant with id 5 not found\" }")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/manager")
    @ResponseBody
    @AdminOnly
    public ResponseEntity<?> addManager(@Valid @RequestBody AssignManagerDto assignManagerDto) {
        return ResponseEntity.ok(adminService.addManager(assignManagerDto));
    }

    @Operation(summary = "Add a new restaurant", description = "Registers a new restaurant in the system. Admin-only operation.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Restaurant added successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Restaurant.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/restaurant")
    @ResponseBody
    @AdminOnly
    public ResponseEntity<?> addRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(adminService.addRestaurant(restaurantDto));
    }

}
