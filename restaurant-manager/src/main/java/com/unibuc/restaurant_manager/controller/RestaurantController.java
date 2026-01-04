package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.model.Dish;
import com.unibuc.restaurant_manager.model.Drink;
import com.unibuc.restaurant_manager.model.Restaurant;
import com.unibuc.restaurant_manager.service.RestaurantService;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/restaurant")
@Tag(name = "Restaurant", description = "Operations related to restaurants and their menus")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Operation(summary = "Get restaurant by ID", description = "Returns a single restaurant by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Restaurant found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Restaurant.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Restaurant not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"No restaurant found at id 20\" }"
                            )
                    )
            )
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getManager(@RequestParam(value = "id", required = true) Integer restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }



    @Operation(summary = "Get all restaurants", description = "Returns a list of all restaurants")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of restaurants",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Restaurant.class)
                    )
            )
    })
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllManagers() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @Operation(summary = "Get dishes by restaurant ID", description = "Returns all dishes for a given restaurant")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of dishes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Dish.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Restaurant not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"No restaurant found at id 20\" }"
                            )
                    )
            )
    })
    @GetMapping("dishes")
    @ResponseBody
    public ResponseEntity<?> getDishesByRestaurantId(@RequestParam(value = "id", required = true) Integer restaurantId) {
        return ResponseEntity.ok(restaurantService.getDishesByRestaurantId(restaurantId));
    }

    @Operation(summary = "Get drinks by restaurant ID", description = "Returns all drinks for a given restaurant")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of drinks",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Drink.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Restaurant not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"No restaurant found at id 20\" }"
                            )
                    )
            )
    })
    @GetMapping("drinks")
    @ResponseBody
    public ResponseEntity<?> getDrinksByRestaurantId(@RequestParam(value = "id", required = true) Integer restaurantId) {
        return ResponseEntity.ok(restaurantService.getDrinksByRestaurantId(restaurantId));
    }

}
