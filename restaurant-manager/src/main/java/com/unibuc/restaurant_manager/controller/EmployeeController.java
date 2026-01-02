package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getEmployee(@RequestParam(value = "id", required = true) Integer employeeId) {
        return ResponseEntity.ok(employeeService.getUserById(employeeId));
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllUsers());
    }

}
