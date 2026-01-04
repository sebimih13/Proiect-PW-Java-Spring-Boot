package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.ManagerOnly;
import com.unibuc.restaurant_manager.dto.AssignEmployeeDto;
import com.unibuc.restaurant_manager.dto.ManagerDto;
import com.unibuc.restaurant_manager.dto.PurchaseOrderResponseDto;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.service.ManagerService;
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
@RequestMapping("/employee/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Operation(summary = "Get manager by ID", description = "Returns a manager by their ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Manager found",
                    content = @Content(schema = @Schema(implementation = ManagerDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Manager not found",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Manager with id 5 not found\" }")))
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getManager(@RequestParam(value = "id", required = true) Integer managerId) {
        return ResponseEntity.ok(managerService.getUserById(managerId));
    }

    @Operation(summary = "Get all managers", description = "Returns all managers")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of managers",
                    content = @Content(schema = @Schema(implementation = ManagerDto.class)))
    })
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllManagers() {
        return ResponseEntity.ok(managerService.getAllUsers());
    }

    @Operation(summary = "Update logged manager", description = "Updates the profile of the logged-in manager")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Manager updated successfully",
                    content = @Content(schema = @Schema(implementation = ManagerDto.class))),
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
    @ManagerOnly
    public ResponseEntity<?> updateManager(@Valid @RequestBody ManagerDto managerDto) {
        return ResponseEntity.ok(managerService.updateLoggedUser(managerDto));
    }

    @Operation(summary = "Change employee salary", description = "Change the salary of an employee in your team")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Salary updated",
                    content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{ \"success\": \"Salary of employee with id ';' has been changed to 4000\" }"))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"Employee with id '3' not found\" }"),
                                    @ExampleObject(value = "{ \"error\": \"Employee with id 3 is not in your team\" }")
                            }))
    })
    @PutMapping("/salary")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> changeSalary(@RequestParam(value = "id", required = true) Integer employeeId, @RequestParam(value = "salary", required = true) Integer newSalary) {
        return ResponseEntity.ok(managerService.changeSalary(employeeId, newSalary));
    }

    @Operation(summary = "Get my team", description = "Returns all employees managed by the logged-in manager")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of employees",
                    content = @Content(schema = @Schema(implementation = Employee.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/myteam")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> getMyTeam() {
        return ResponseEntity.ok(managerService.getMyTeam());
    }

    @Operation(summary = "Assign an employee", description = "Assigns a role to an employee under the logged-in manager")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee assigned successfully",
                    content = @Content(schema = @Schema(implementation = Employee.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"\"Employee with id '4' not found\"\" }"),
                                    @ExampleObject(value = "{ \"error\": \"You cannot assign yourself to a role\" }"),
                                    @ExampleObject(value = "{ \"error\": \"Access denied\" }")
                            }))
    })
    @PutMapping("/assign")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> assignEmployee(@Valid @RequestBody AssignEmployeeDto assignEmployeeDto) {
        return ResponseEntity.ok(managerService.assignEmployee(assignEmployeeDto));
    }

    @Operation(summary = "Remove an employee", description = "Removes an employee from the manager's team")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee removed",
                    content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{ \"success\": \"Employee with id 3 has been removed from your team\" }"))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"\"Employee with id '4' not found\"\" }"),
                                    @ExampleObject(value = "{ \"error\": \"Employee with id '4' is not in your team\" }")
                            }))
    })
    @PutMapping("/remove")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> removeEmployee(@RequestParam(value = "id", required = true) Integer employeeId) {
        return ResponseEntity.ok(managerService.removeEmployee(employeeId));
    }


    @Operation(summary = "Get all orders", description = "Returns all purchase orders for the manager's restaurant")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of purchase orders",
                    content = @Content(schema = @Schema(implementation = PurchaseOrderResponseDto.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"Manager does not have a restaurant assigned\" }")))
    })
    @GetMapping("/orders/all")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(managerService.getAllOrders());
    }

    @Operation(summary = "Complete an order", description = "Completes a pending purchase order for the manager's restaurant")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order completed",
                    content = @Content(schema = @Schema(implementation = PurchaseOrderResponseDto.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{ \"error\": \"Purchase order with id '4' not found\" }"),
                                    @ExampleObject(value = "{ \"error\": \"You can only complete orders from your own restaurant\" }"),
                                    @ExampleObject(value = "{ \"error\": \"Only pending orders can be completed\" }"),
                            }))
    })
    @PutMapping("/orders/complete")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> completeOrder(@RequestParam(value = "id", required = true) Integer purchadeOrderId) {
        return ResponseEntity.ok(managerService.completeOrder(purchadeOrderId));
    }

}
