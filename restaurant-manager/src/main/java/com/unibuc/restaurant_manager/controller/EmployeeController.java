package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllUsers());
    }

}
