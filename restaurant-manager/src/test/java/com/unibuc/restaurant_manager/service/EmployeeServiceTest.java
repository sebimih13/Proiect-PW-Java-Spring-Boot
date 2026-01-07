package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.mapper.EmployeeMapper;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = Employee.builder()
                .id(10)
                .username("employee")
                .password(JWTService.encryptPassword("employee123-PASSWORD"))
                .lastName("Employee-LastName")
                .firstName("Employee-FirstName")
                .email("employee.mail@gmial.com")
                .phoneNumber("0712345678")
                .birthDate(LocalDate.of(1990, 1, 1))
                .CNP("1234567890123")
                .IDSeries("RL")
                .IDNumber("123456")
                .build();
    }

    @Test
    void findAll_ShouldReturnEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<Employee> result = employeeService.getRepository().findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("employee", result.get(0).getUsername());
        assertEquals("Employee-FirstName", result.get(0).getFirstName());
        assertEquals("Employee-LastName", result.get(0).getLastName());
        assertEquals("employee.mail@gmial.com", result.get(0).getEmail());
        assertEquals("0712345678", result.get(0).getPhoneNumber());

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenExists_ShouldReturnEmployee() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getRepository().findById(1);

        assertTrue(result.isPresent());
        assertEquals(employee, result.get());

        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmpty() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.getRepository().findById(1);

        assertTrue(result.isEmpty());
        verify(employeeRepository, times(1)).findById(1);
    }

}
