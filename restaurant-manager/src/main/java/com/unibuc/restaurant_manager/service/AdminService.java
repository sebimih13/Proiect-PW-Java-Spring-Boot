package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.PromoteManagerDto;
import com.unibuc.restaurant_manager.dto.RestaurantDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.model.Manager;
import com.unibuc.restaurant_manager.model.Restaurant;
import com.unibuc.restaurant_manager.repository.EmployeeRepository;
import com.unibuc.restaurant_manager.repository.ManagerRepository;
import com.unibuc.restaurant_manager.repository.RestaurantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Manager addManager(PromoteManagerDto promoteManagerDto) {
        Employee employee = employeeRepository.findById(promoteManagerDto.getEmployeeID())
                .orElseThrow(() -> new NotFoundException(String.format("Employee with id '%d' not found", promoteManagerDto.getEmployeeID())));

        Restaurant restaurant = restaurantRepository.findById(promoteManagerDto.getRestaurantId())
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id '%d' not found", promoteManagerDto.getRestaurantId())));

        if (employee instanceof Manager) {
            throw new ValidationException(String.format("Employee with id '%d' is already a manager", promoteManagerDto.getEmployeeID()));
        }

        employee.setRestaurant(restaurant);
        Manager newManager = Manager.fromEmployee(employee);
        employeeRepository.delete(employee);

        return managerRepository.save(newManager);
    }

    public Restaurant addRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDto.getName())
                .stars(restaurantDto.getStars())
                .city(restaurantDto.getCity())
                .address(restaurantDto.getAddress())
                .phoneNumber(restaurantDto.getPhoneNumber())
                .build();

        return restaurantRepository.save(restaurant);
    }

}
