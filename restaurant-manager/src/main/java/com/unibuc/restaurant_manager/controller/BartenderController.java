package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.BartenderOnly;
import com.unibuc.restaurant_manager.dto.BartenderDto;
import com.unibuc.restaurant_manager.dto.DrinkDto;
import com.unibuc.restaurant_manager.model.Drink;
import com.unibuc.restaurant_manager.service.BartenderService;
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

import java.util.Map;

@RestController
@RequestMapping("/employee/bartender")
public class BartenderController {

    @Autowired
    private BartenderService bartenderService;

    @Operation(summary = "Get bartender by ID", description = "Returns a bartender by their ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Bartender found",
                    content = @Content(schema = @Schema(implementation = BartenderDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bartender not found",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Bartender with id 5 not found\" }")))
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getBartender(@RequestParam(value = "id", required = true) Integer bartenderId) {
        return ResponseEntity.ok(bartenderService.getUserById(bartenderId));
    }

    @Operation(summary = "Get all bartenders", description = "Returns all bartenders")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of bartenders",
                    content = @Content(schema = @Schema(implementation = BartenderDto.class)))
    })
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllBartenders() {
        return ResponseEntity.ok(bartenderService.getAllUsers());
    }

    @Operation(summary = "Update logged bartender", description = "Updates the profile of the logged-in bartender")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Bartender updated successfully",
                    content = @Content(schema = @Schema(implementation = BartenderDto.class))),
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
    @BartenderOnly
    public ResponseEntity<?> updateBartender(@Valid @RequestBody BartenderDto bartenderDto) {
        return ResponseEntity.ok(bartenderService.updateLoggedUser(bartenderDto));
    }

    @Operation(summary = "Get my drinks", description = "Returns all drinks made by the logged-in bartender")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of drinks",
                    content = @Content(schema = @Schema(implementation = Drink.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/mydrinks")
    @ResponseBody
    @BartenderOnly
    public ResponseEntity<?> getMyDrinks() {
        return ResponseEntity.ok(bartenderService.getMyDrinks());
    }

    @Operation(summary = "Add a new purchase drink", description = "Creates a new drink for the logged-in bartender")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Drink created successfully",
                    content = @Content(schema = @Schema(implementation = Drink.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/add")
    @ResponseBody
    @BartenderOnly
    public ResponseEntity<?> addDrink(@Valid @RequestBody DrinkDto drinkDto) {
        return ResponseEntity.ok(bartenderService.addDrink(drinkDto));
    }

    @Operation(summary = "Edit an existing drink", description = "Edits a drink for the logged-in bartender")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Drink edited successfully",
                    content = @Content(schema = @Schema(implementation = Drink.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"Drink with id '15' not found\" }"),
                                    @ExampleObject(value = "{ \"error\": \"You cannot update a drink you don't own\" }")
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
    @BartenderOnly
    public ResponseEntity<?> editDrink(@RequestParam(value = "id", required = true) Integer drinkId, @Valid @RequestBody DrinkDto drinkDto) {
        return ResponseEntity.ok(bartenderService.updateDrink(drinkId, drinkDto));
    }

    @Operation(summary = "Remove a drink", description = "Removes a drink for the logged-in bartender")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Drink removed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(
                                    value = "{ \"success\": \"Drink with id '15' has been removed successfully\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"Drink with id '15' not found\" }"),
                                    @ExampleObject(value = "{ \"error\": \"You can only remove your own drinks\" }")
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
    @BartenderOnly
    public ResponseEntity<?> removeEmployee(@RequestParam(value = "id", required = true) Integer drinkId) {
        return ResponseEntity.ok(bartenderService.removeDrink(drinkId));
    }

}
