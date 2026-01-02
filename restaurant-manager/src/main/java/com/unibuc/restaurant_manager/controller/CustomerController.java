package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.CustomerOnly;
import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.dto.PurchaseOrderDto;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.model.PurchaseOrder;
import com.unibuc.restaurant_manager.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllUsers());
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getCustomers(@RequestParam(value = "Id", required = true) Integer customerId) {
        return ResponseEntity.ok(customerService.getUserById(customerId));
    }

    @PutMapping("")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody CustomerDto customer) {
        return ResponseEntity.ok(customerService.updateLoggedUser(customer));
    }

    @GetMapping("/orders")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<?> getMyOrders() {
        return ResponseEntity.ok(customerService.getMyOrders());
    }

    @PostMapping("/orders")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<?> addOrder(@Valid @RequestBody PurchaseOrderDto order) {
        return ResponseEntity.ok(customerService.addOrder(order));
    }

}
