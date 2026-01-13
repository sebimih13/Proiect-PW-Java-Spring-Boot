package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.dto.EmployeeDto;
import com.unibuc.restaurant_manager.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Get employee by ID", description = "Returns an employee by their ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee found",
                    content = @Content(schema = @Schema(implementation = EmployeeDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation Error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"Employee with id 5 not found\" }")
                    )
            )
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getEmployee(@RequestParam(value = "id", required = true) Integer employeeId) {
        return ResponseEntity.ok(employeeService.getUserById(employeeId));
    }

    @Operation(summary = "Get all employees", description = "Returns all employees")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of employees",
                    content = @Content(schema = @Schema(implementation = EmployeeDto.class))
            )
    })
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllUsers());
    }

}
