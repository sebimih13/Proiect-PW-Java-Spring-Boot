package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.AssignManagerDto;
import com.unibuc.restaurant_manager.dto.RestaurantDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.model.Manager;
import com.unibuc.restaurant_manager.model.Restaurant;
import com.unibuc.restaurant_manager.repository.EmployeeRepository;
import com.unibuc.restaurant_manager.repository.ManagerRepository;
import com.unibuc.restaurant_manager.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    void addManager_WhenEmployeeNotFound_ShouldThrowNotFoundException() {
        AssignManagerDto dto = AssignManagerDto.builder()
            .employeeId(1)
            .restaurantId(1)
            .build();

        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.addManager(dto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Employee with id '1' not found");
    }

    @Test
    void addManager_WhenRestaurantNotFound_ShouldThrowNotFoundException() {
        AssignManagerDto dto = AssignManagerDto.builder()
            .employeeId(1)
            .restaurantId(1)
            .build();

        Employee employee = Employee.builder()
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

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(restaurantRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.addManager(dto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Restaurant with id '1' not found");
    }

    @Test
    void addManager_WhenEmployeeIsAlreadyManager_ShouldThrowValidationException() {
        AssignManagerDto dto = AssignManagerDto.builder()
            .employeeId(1)
            .restaurantId(1)
            .build();

        Manager manager = Manager.builder()
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
                .educationLevel("top education")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .id(1)
                .name("Restaurant Test")
                .stars(4)
                .city("Bucharest")
                .address("Restaurant Address")
                .phoneNumber("0123456789")
                .build();

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));
        when(employeeRepository.findById(1)).thenReturn(Optional.of(manager));

        assertThatThrownBy(() -> adminService.addManager(dto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Employee with id '1' is already a manager");
    }

    @Test
    void addManager_WhenValid_ShouldSaveManager() {
        AssignManagerDto dto = AssignManagerDto.builder()
            .employeeId(1)
            .restaurantId(2)
            .build();

        Employee employee = Employee.builder()
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

        Restaurant restaurant = Restaurant.builder()
                .id(2)
                .name("Restaurant Test")
                .stars(4)
                .city("Bucharest")
                .address("Restaurant Address")
                .phoneNumber("0123456789")
                .build();

        Manager savedManager = Manager.builder()
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
                .educationLevel("top education")
                .build();

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(restaurantRepository.findById(2)).thenReturn(Optional.of(restaurant));
        when(managerRepository.save(any(Manager.class))).thenReturn(savedManager);

        Manager result = adminService.addManager(dto);

        assertThat(result).isEqualTo(savedManager);
        assertThat(employee.getRestaurant()).isEqualTo(restaurant);

        verify(employeeRepository).delete(employee);
        verify(managerRepository).save(any(Manager.class));
    }

    @Test
    void addRestaurant_ShouldSaveRestaurant() {
        RestaurantDto restaurantInput = RestaurantDto.builder()
                .name("Restaurant Test")
                .stars(4)
                .city("Bucharest")
                .address("Restaurant Address")
                .phoneNumber("0123456789")
                .build();

        Restaurant saved = Restaurant.builder()
                .id(1)
                .name("Restaurant Test")
                .stars(4)
                .city("Bucharest")
                .address("Restaurant Address")
                .phoneNumber("0123456789")
                .build();

        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(saved);

        Restaurant result = adminService.addRestaurant(restaurantInput);

        verify(restaurantRepository, times(1)).save(
                argThat(restaurant
                        -> restaurant.getName().equals(restaurantInput.getName())
                        && restaurant.getStars().equals(restaurantInput.getStars())
                        && restaurant.getCity().equals(restaurantInput.getCity())
                        && restaurant.getAddress().equals(restaurantInput.getAddress())
                        && restaurant.getPhoneNumber().equals(restaurantInput.getPhoneNumber())
                )
        );
    }

}
