package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.dto.EmployeeDto;
import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.dto.CredentialsDto;
import com.unibuc.restaurant_manager.dto.TokenDto;
import com.unibuc.restaurant_manager.model.User;
import com.unibuc.restaurant_manager.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDto> login(@Valid @RequestBody CredentialsDto credentials) {
        return ResponseEntity.ok(authenticationService.login(credentials));
    }

    @PostMapping("/signup/customer")
    @ResponseBody
    public ResponseEntity<User> signupCustomer(@Valid @RequestBody CustomerDto customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signupCustomer(customer));
    }

    @PostMapping("/signup/employee")
    @ResponseBody
    public ResponseEntity<User> signupEmployee(@Valid @RequestBody EmployeeDto employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signupEmployee(employee));
    }

}
