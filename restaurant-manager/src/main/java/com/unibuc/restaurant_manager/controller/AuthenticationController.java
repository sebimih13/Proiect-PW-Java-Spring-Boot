package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.dto.EmployeeDto;
import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.dto.CredentialsDto;
import com.unibuc.restaurant_manager.dto.TokenDto;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.model.User;
import com.unibuc.restaurant_manager.service.AuthenticationService;
import com.unibuc.restaurant_manager.validation.OnCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Login", description = "Authenticate a user with username/email and password. Returns a JWT token on success.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenDto.class),
                            examples = @ExampleObject(
                                    value = "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Invalid username or password\" }"
                            )
                    )
            )
    })
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDto> login(@Valid @RequestBody CredentialsDto credentials) {
        return ResponseEntity.ok(authenticationService.login(credentials));
    }

    @Operation(summary = "Sign up as Customer", description = "Registers a new customer account.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error or duplicate username/email",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Username already exists\" }"
                            )
                    )
            )
    })
    @PostMapping("/signup/customer")
    @ResponseBody
    public ResponseEntity<User> signupCustomer(@Valid @RequestBody @Validated(OnCreate.class) CustomerDto customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signupCustomer(customer));
    }

    @Operation(summary = "Sign up as Employee", description = "Registers a new employee account.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Employee created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error or duplicate username/email",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"Email already exists\" }"
                            )
                    )
            )
    })
    @PostMapping("/signup/employee")
    @ResponseBody
    public ResponseEntity<User> signupEmployee(@Valid @RequestBody @Validated(OnCreate.class) EmployeeDto employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signupEmployee(employee));
    }

}
