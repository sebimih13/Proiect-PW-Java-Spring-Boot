package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.AssignEmployeeDto;
import com.unibuc.restaurant_manager.dto.ManagerDto;
import com.unibuc.restaurant_manager.dto.PurchaseOrderResponseDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.mapper.ManagerMapper;
import com.unibuc.restaurant_manager.mapper.PurchaseOrderMapper;
import com.unibuc.restaurant_manager.model.*;
import com.unibuc.restaurant_manager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public final class ManagerService extends UserService<Manager, ManagerDto> {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private BartenderRepository bartenderRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private JWTService jwtService;

    @Override
    protected JpaRepository<Manager, Integer> getRepository() {
        return managerRepository;
    }

    @Override
    protected String getUserEntityName() {
        return "Manager";
    }

    @Override
    protected ManagerMapper getMapper() {
        return managerMapper;
    }

    public List<Employee> getMyTeam() {
        Manager manager = (Manager) jwtService.getUser();
        return employeeRepository.findByManager(manager);
    }

    public Map<String, String> changeSalary(Integer employeeId, Integer newSalary) {
        Manager manager = (Manager) jwtService.getUser();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(String.format("Employee with id '%d' not found", employeeId)));

        if (!employee.getManager().getId().equals(manager.getId())) {
            throw new ValidationException(String.format("Employee with id '%d' is not in your team", employeeId));
        }

        employee.setSalary(newSalary);
        employeeRepository.save(employee);

        return Map.of("success", String.format("Salary of employee with id '%d' has been changed to %d", employeeId, newSalary));
    }

    public Employee assignEmployee(AssignEmployeeDto assignEmployeeDto) {
        Manager manager = (Manager) jwtService.getUser();
        Employee employee = employeeRepository.findById(assignEmployeeDto.getEmployeeId())
                .orElseThrow(() -> new ValidationException(String.format("Employee with id '%d' not found", assignEmployeeDto.getEmployeeId())));

        if (employee.getId().equals(manager.getId())) {
            throw new ValidationException("You cannot assign yourself to a role");
        }

        if (employee.getManager() != null && !employee.getManager().getId().equals(manager.getId())) {
            throw new ValidationException(String.format("Employee with id '%d' is already assigned to a manager", assignEmployeeDto.getEmployeeId()));
        }

        employee.setManager(manager);
        employee.setRestaurant(manager.getRestaurant());
        if (employee.getEmploymentDate() == null) {
            employee.setEmploymentDate(LocalDate.now());
        }

        switch (assignEmployeeDto.getRole()) {
            case COOK -> {
                Cook newCook = Cook.fromEmployee(employee);
                employeeRepository.delete(employee);
                return cookRepository.save(newCook);
            }

            case BARTENDER -> {
                Bartender newBartender = Bartender.fromEmployee(employee);
                employeeRepository.delete(employee);
                return bartenderRepository.save(newBartender);
            }

            default -> {
                throw new IllegalArgumentException("Unexpected role: " + assignEmployeeDto.getRole().toString());
            }
        }
    }

    public Map<String, String> removeEmployee(Integer employeeId) {
        Manager manager = (Manager) jwtService.getUser();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(String.format("Employee with id '%d' not found", employeeId)));

        if (employee.getManager() == null || !employee.getManager().getId().equals(manager.getId())) {
            throw new ValidationException(String.format("Employee with id '%d' is not in your team", employeeId));
        }

        employee.setRestaurant(null);
        employee.setManager(null);
        employeeRepository.save(employee);

        return Map.of("success", String.format("Employee with id '%d' has been removed from your team", employeeId));
    }

    public List<PurchaseOrderResponseDto> getAllOrders() {
        Manager manager = (Manager) jwtService.getUser();
        if (manager.getRestaurant() == null) {
            throw new ValidationException("Manager does not have a restaurant assigned");
        }

        List<PurchaseOrder> orders = purchaseOrderRepository.findByRestaurant(manager.getRestaurant());

        return orders.stream()
                .map(purchaseOrderMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public PurchaseOrderResponseDto completeOrder(Integer purchaseOrderId) {
        Manager manager = (Manager) jwtService.getUser();
        PurchaseOrder order = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new NotFoundException(String.format("Purchase order with id '%d' not found", purchaseOrderId)));

        if (!order.getRestaurant().getId().equals(manager.getRestaurant().getId())) {
            throw new ValidationException("You can only complete orders from your own restaurant");
        }

        if (!order.getStatus().equals(PurchaseOrder.Status.PENDING.toString())) {
            throw new ValidationException("Only pending orders can be completed");
        }

        order.setStatus(PurchaseOrder.Status.COMPLETED.toString());
        purchaseOrderRepository.save(order);

        return purchaseOrderMapper.toResponseDto(order);
    }

}
