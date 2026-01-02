package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.CustomerOnly;
import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getCustomers(@RequestParam(value = "customerId", required = false) Integer customerId) {
        if (customerId != null) {
            Customer customer = customerService.getUserById(customerId);
            return ResponseEntity.ok(customer);
        } else {
            List<Customer> customers = customerService.getAllUsers();
            return ResponseEntity.ok(customers);
        }
    }

    @PutMapping("")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody CustomerDto customer) {
        return ResponseEntity.ok(customerService.updateLoggedUser(customer));
    }

}
