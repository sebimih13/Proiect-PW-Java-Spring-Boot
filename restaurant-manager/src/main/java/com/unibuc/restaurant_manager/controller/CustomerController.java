package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.CustomerOnly;
import com.unibuc.restaurant_manager.dto.*;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.service.CustomerService;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Get customer by ID", description = "Returns a customer by their ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer found",
                    content = @Content(schema = @Schema(implementation = CustomerDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Customer not found",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Customer with id 5 not found\" }")))
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getCustomer(@RequestParam(value = "id", required = true) Integer customerId) {
        return ResponseEntity.ok(customerService.getUserById(customerId));
    }

    @Operation(summary = "Get all customers", description = "Returns all customers")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of customers",
                    content = @Content(schema = @Schema(implementation = CustomerDto.class)))
    })
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllUsers());
    }

    @Operation(summary = "Update logged customer", description = "Updates the profile of the logged-in customer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer updated successfully",
                    content = @Content(schema = @Schema(implementation = CustomerDto.class))),
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
    @CustomerOnly
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.updateLoggedUser(customerDto));
    }

    @Operation(summary = "Get my orders", description = "Returns all orders made by the logged-in customer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of orders",
                    content = @Content(schema = @Schema(implementation = Employee.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/orders")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<?> getMyOrders() {
        return ResponseEntity.ok(customerService.getMyOrders());
    }

    @Operation(summary = "Add a new purchase order", description = "Creates a new purchase order for the logged-in customer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = PurchaseOrderResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"Restaurant with id '10' not found\" }"),
                                    @ExampleObject(value = "{ \"error\": \"Product with id '5' not found\" }")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/orders")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<?> addOrder(@Valid @RequestBody NewPurchaseOrderDto newPurchaseOrderDto) {
        return ResponseEntity.ok(customerService.addOrder(newPurchaseOrderDto));
    }

    @Operation(summary = "Edit an existing purchase order", description = "Edits a purchase order for the logged-in customer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order edited successfully",
                    content = @Content(schema = @Schema(implementation = PurchaseOrderResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"Order with id '15' not found\" }"),
                                    @ExampleObject(value = "{ \"error\": \"You can only edit your own orders\" }"),
                                    @ExampleObject(value = "{ \"error\": \"Only pending orders can be edited\" }"),
                                    @ExampleObject(value = "{ \"error\": \"Product with id '5' not found\" }")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/orders")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<?> editOrder(@RequestParam(value = "id", required = true) Integer orderId, @Valid @RequestBody EditPurchaseOrderDto editPurchaseOrderDto) {
        return ResponseEntity.ok(customerService.editOrder(orderId, editPurchaseOrderDto));
    }

    @Operation(summary = "Remove a purchase order", description = "Removes a purchase order for the logged-in customer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order removed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(
                                    value = "{ \"success\": \"Order with id '15' has been removed successfully\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"Order with id '15' not found\" }"),
                                    @ExampleObject(value = "{ \"error\": \"You can only remove your own orders\" }"),
                                    @ExampleObject(value = "{ \"error\": \"Only pending orders can be removed\" }"),
                                    @ExampleObject(value = "{ \"error\": \"Product with id '5' not found\" }")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/orders")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<?> removeOrder(@RequestParam(value = "id", required = true) Integer orderId) {
        return ResponseEntity.ok(customerService.removeOrder(orderId));
    }

}
