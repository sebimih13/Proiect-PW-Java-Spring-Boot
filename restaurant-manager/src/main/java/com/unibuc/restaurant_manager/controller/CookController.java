package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.CookOnly;
import com.unibuc.restaurant_manager.dto.CookDto;
import com.unibuc.restaurant_manager.dto.DishDto;
import com.unibuc.restaurant_manager.model.Dish;
import com.unibuc.restaurant_manager.service.CookService;
import com.unibuc.restaurant_manager.validation.OnCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employee/cook")
public class CookController {

    @Autowired
    private CookService cookService;

    @Operation(summary = "Get cook by ID", description = "Returns a cook by their ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cook found",
                    content = @Content(schema = @Schema(implementation = CookDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cook not found",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Cook with id 5 not found\" }")))
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getCook(@RequestParam(value = "id", required = true) Integer cookId) {
        return ResponseEntity.ok(cookService.getUserById(cookId));
    }

    @Operation(summary = "Get all cooks", description = "Returns all cooks")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of cooks",
                    content = @Content(schema = @Schema(implementation = CookDto.class)))
    })
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllCooks() {
        return ResponseEntity.ok(cookService.getAllUsers());
    }

    @Operation(summary = "Update logged cook", description = "Updates the profile of the logged-in cook")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cook updated successfully",
                    content = @Content(schema = @Schema(implementation = CookDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"First name must not be blank\" }"))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("")
    @ResponseBody
    @CookOnly
    public ResponseEntity<?> updateCook(@Valid @RequestBody CookDto cookDto) {
        return ResponseEntity.ok(cookService.updateLoggedUser(cookDto));
    }

    @Operation(summary = "Get my dishes", description = "Returns all dishes made by the logged-in cook")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of dishes",
                    content = @Content(schema = @Schema(implementation = Dish.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/mydishes")
    @ResponseBody
    @CookOnly
    public ResponseEntity<?> getMyDishes() {
        return ResponseEntity.ok(cookService.getMyDishes());
    }

    @Operation(summary = "Add a new purchase dish", description = "Creates a new dish for the logged-in cook")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dish created successfully",
                    content = @Content(schema = @Schema(implementation = Dish.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/add")
    @ResponseBody
    @CookOnly
    public ResponseEntity<?> addDish(@Valid @RequestBody @Validated(OnCreate.class) DishDto dishDto) {
        return ResponseEntity.ok(cookService.addDish(dishDto));
    }

    @Operation(summary = "Edit an existing dish", description = "Edits a dish for the logged-in cook")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dish edited successfully",
                    content = @Content(schema = @Schema(implementation = Dish.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"Dish with id '15' not found\" }"),
                                    @ExampleObject(value = "{ \"error\": \"You cannot update a dish you don't own\" }")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/edit")
    @ResponseBody
    @CookOnly
    public ResponseEntity<?> editDish(@RequestParam(value = "id", required = true) Integer dishId, @Valid @RequestBody DishDto dishDto) {
        return ResponseEntity.ok(cookService.updateDish(dishId, dishDto));
    }

    @Operation(summary = "Remove a dish", description = "Removes a dish for the logged-in cook")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dish removed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(
                                    value = "{ \"success\": \"Dish with id '15' has been removed successfully\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"Dish with id '15' not found\" }"),
                                    @ExampleObject(value = "{ \"error\": \"You can only remove your own dishes\" }")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/remove")
    @ResponseBody
    @CookOnly
    public ResponseEntity<?> removeEmployee(@RequestParam(value = "id", required = true) Integer dishId) {
        return ResponseEntity.ok(cookService.removeDish(dishId));
    }

}
