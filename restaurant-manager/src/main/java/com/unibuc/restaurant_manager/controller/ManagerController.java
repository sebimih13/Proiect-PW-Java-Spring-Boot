package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.annotation.ManagerOnly;
import com.unibuc.restaurant_manager.dto.ManagerDto;
import com.unibuc.restaurant_manager.model.Manager;
import com.unibuc.restaurant_manager.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Manager>> getAllManagers() {
        return ResponseEntity.ok(managerService.getAllUsers());
    }

    @PutMapping("")
    @ResponseBody
    @ManagerOnly
    public ResponseEntity<Manager> updateManager(@Valid @RequestBody ManagerDto managerDto) {
        return ResponseEntity.ok(managerService.updateLoggedUser(managerDto));
    }

}
