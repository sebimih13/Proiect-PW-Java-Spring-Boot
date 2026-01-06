package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.AssignEmployeeDto;
import com.unibuc.restaurant_manager.dto.PurchaseOrderResponseDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.mapper.ManagerMapper;
import com.unibuc.restaurant_manager.mapper.PurchaseOrderMapper;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.model.Manager;
import com.unibuc.restaurant_manager.model.PurchaseOrder;
import com.unibuc.restaurant_manager.model.Restaurant;
import com.unibuc.restaurant_manager.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CookRepository cookRepository;

    @Mock
    private BartenderRepository bartenderRepository;

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private ManagerMapper managerMapper;

    @Mock
    private PurchaseOrderMapper purchaseOrderMapper;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private ManagerService managerService;

    private Manager manager;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        manager = Manager.builder()
                .id(1)
                .username("manager")
                .firstName("Manager")
                .lastName("User")
                .restaurant(Restaurant.builder().id(1).build())
                .build();

        employee = Employee.builder()
                .id(2)
                .firstName("Employee")
                .lastName("User")
                .build();

        when(jwtService.getUser()).thenReturn(manager);
    }

    @Test
    void getMyTeam_ShouldReturnEmployees() {
        when(employeeRepository.findByManager(manager)).thenReturn(List.of(employee));

        List<Employee> team = managerService.getMyTeam();

        assertEquals(1, team.size());
        assertEquals(employee, team.get(0));
        verify(employeeRepository, times(1)).findByManager(manager);
    }

    @Test
    void changeSalary_WhenEmployeeNotFound_ShouldThrowNotFoundException() {
        when(employeeRepository.findById(2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.changeSalary(2, 5000))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Employee with id '2' not found");
    }

    @Test
    void changeSalary_WhenEmployeeNotInTeam_ShouldThrowValidationException() {
        Employee otherEmployee = Employee.builder().id(2).manager(Manager.builder().id(99).build()).build();
        when(employeeRepository.findById(2)).thenReturn(Optional.of(otherEmployee));

        assertThatThrownBy(() -> managerService.changeSalary(2, 5000))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Employee with id '2' is not in your team");
    }

    @Test
    void changeSalary_WhenValid_ShouldUpdateSalary() {
        employee.setManager(manager);
        when(employeeRepository.findById(2)).thenReturn(Optional.of(employee));

        Map<String, String> result = managerService.changeSalary(2, 5000);

        assertEquals("Salary of employee with id '2' has been changed to 5000", result.get("success"));
        assertEquals(5000, employee.getSalary());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void assignEmployee_WhenEmployeeNotFound_ShouldThrowValidationException() {
        AssignEmployeeDto dto = new AssignEmployeeDto(2, AssignEmployeeDto.Role.COOK);
        when(employeeRepository.findById(2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.assignEmployee(dto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Employee with id '2' not found");
    }

    @Test
    void assignEmployee_WhenEmployeeAlreadyAssigned_ShouldThrowValidationException() {
        AssignEmployeeDto dto = new AssignEmployeeDto(2, AssignEmployeeDto.Role.COOK);
        employee.setManager(Manager.builder().id(99).build());
        when(employeeRepository.findById(2)).thenReturn(Optional.of(employee));

        assertThatThrownBy(() -> managerService.assignEmployee(dto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Employee with id '2' is already assigned to a manager");
    }

    @Test
    void removeEmployee_WhenEmployeeNotInTeam_ShouldThrowValidationException() {
        when(employeeRepository.findById(2)).thenReturn(Optional.of(employee));

        assertThatThrownBy(() -> managerService.removeEmployee(2))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Employee with id '2' is not in your team");
    }

    @Test
    void removeEmployee_WhenValid_ShouldRemoveEmployeeFromTeam() {
        employee.setManager(manager);
        employee.setRestaurant(manager.getRestaurant());
        when(employeeRepository.findById(2)).thenReturn(Optional.of(employee));

        Map<String, String> result = managerService.removeEmployee(2);

        assertEquals("Employee with id '2' has been removed from your team", result.get("success"));
        assertNull(employee.getManager());
        assertNull(employee.getRestaurant());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void getAllOrders_WhenManagerHasNoRestaurant_ShouldThrowValidationException() {
        manager.setRestaurant(null);

        assertThatThrownBy(() -> managerService.getAllOrders())
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Manager does not have a restaurant assigned");
    }

    @Test
    void completeOrder_WhenOrderNotFound_ShouldThrowNotFoundException() {
        when(purchaseOrderRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.completeOrder(1))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Purchase order with id '1' not found");
    }

    @Test
    void completeOrder_WhenOrderNotFromManagerRestaurant_ShouldThrowValidationException() {
        PurchaseOrder order = PurchaseOrder.builder()
                .id(1)
                .restaurant(Restaurant.builder().id(99).build())
                .status(PurchaseOrder.Status.PENDING.toString())
                .build();
        when(purchaseOrderRepository.findById(1)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> managerService.completeOrder(1))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("You can only complete orders from your own restaurant");
    }

    @Test
    void completeOrder_WhenPending_ShouldCompleteSuccessfully() {
        PurchaseOrder order = PurchaseOrder.builder()
                .id(1)
                .restaurant(manager.getRestaurant())
                .status(PurchaseOrder.Status.PENDING.toString())
                .build();

        when(purchaseOrderRepository.findById(1)).thenReturn(Optional.of(order));
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        when(purchaseOrderMapper.toResponseDto(order)).thenReturn(dto);

        PurchaseOrderResponseDto result = managerService.completeOrder(1);

        assertEquals(dto, result);
        assertEquals(PurchaseOrder.Status.COMPLETED.toString(), order.getStatus());
        verify(purchaseOrderRepository, times(1)).save(order);
    }

}
