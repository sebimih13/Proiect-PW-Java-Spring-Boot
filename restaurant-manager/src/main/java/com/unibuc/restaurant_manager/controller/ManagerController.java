package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.ManagerOnly;
import com.unibuc.restaurant_manager.dto.AssignEmployeeDto;
import com.unibuc.restaurant_manager.dto.ManagerDto;
import com.unibuc.restaurant_manager.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getManager(@RequestParam(value = "id", required = true) Integer managerId) {
        return ResponseEntity.ok(managerService.getUserById(managerId));
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> getAllManagers() {
        return ResponseEntity.ok(managerService.getAllUsers());
    }

    @PutMapping("")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> updateManager(@Valid @RequestBody ManagerDto managerDto) {
        return ResponseEntity.ok(managerService.updateLoggedUser(managerDto));
    }

    @PutMapping("/salary")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> changeSalary(@RequestParam(value = "id", required = true) Integer employeeId, @RequestParam(value = "salary", required = true) Integer newSalary) {
        return ResponseEntity.ok(managerService.changeSalary(employeeId, newSalary));
    }

    @GetMapping("/myteam")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> getMyTeam() {
        return ResponseEntity.ok(managerService.getMyTeam());
    }

    @PutMapping("/assign")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> assignEmployee(@Valid @RequestBody AssignEmployeeDto assignEmployeeDto) {
        return ResponseEntity.ok(managerService.assignEmployee(assignEmployeeDto));
    }

    @PutMapping("/remove")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> removeEmployee(@RequestParam(value = "id", required = true) Integer employeeId) {
        return ResponseEntity.ok(managerService.removeEmployee(employeeId));
    }

    @GetMapping("/orders/all")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(managerService.getAllOrders());
    }

    @PutMapping("/orders/complete")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<?> completeOrder(@RequestParam(value = "id", required = true) Integer purchadeOrderId) {
        return ResponseEntity.ok(managerService.completeOrder(purchadeOrderId));
    }

}
