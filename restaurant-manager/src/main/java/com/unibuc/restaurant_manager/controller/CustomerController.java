package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.CustomerOnly;
import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.dto.EditPurchaseOrderDto;
import com.unibuc.restaurant_manager.dto.NewPurchaseOrderDto;
import com.unibuc.restaurant_manager.model.Customer;
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

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getCustomer(@RequestParam(value = "id", required = true) Integer customerId) {
        return ResponseEntity.ok(customerService.getUserById(customerId));
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllUsers());
    }

    @PutMapping("")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.updateLoggedUser(customerDto));
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
    public ResponseEntity<?> addOrder(@Valid @RequestBody NewPurchaseOrderDto newPurchaseOrderDto) {
        return ResponseEntity.ok(customerService.addOrder(newPurchaseOrderDto));
    }

    @PutMapping("/orders")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<?> editOrder(@RequestParam(value = "id", required = true) Integer orderId, @Valid @RequestBody EditPurchaseOrderDto editPurchaseOrderDto) {
        return ResponseEntity.ok(customerService.editOrder(orderId, editPurchaseOrderDto));
    }

    @DeleteMapping("/orders")
    @ResponseBody
    @CustomerOnly
    public ResponseEntity<?> removeOrder(@RequestParam(value = "id", required = true) Integer orderId) {
        return ResponseEntity.ok(customerService.removeOrder(orderId));
    }

}
