package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.CredentialsDto;
import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.dto.EmployeeDto;
import com.unibuc.restaurant_manager.dto.TokenDto;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.model.User;
import com.unibuc.restaurant_manager.repository.CustomerRepository;
import com.unibuc.restaurant_manager.repository.EmployeeRepository;
import com.unibuc.restaurant_manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    protected UserRepository userRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testLogin_ValidCredentials() {
        CredentialsDto credentials = new CredentialsDto("customer", "customer123-PASSWORD");
        User user = Customer.builder()
                .id(10)
                .username("customer")
                .password(JWTService.encryptPassword("customer123-PASSWORD"))
                .lastName("Customer-LastName")
                .firstName("Customer-FirstName")
                .email("customer.mail@gmial.com")
                .phoneNumber("0712345678")
                .address("Customer-Address")
                .build();
        when(userRepository.findByUsername(credentials.getUsername())).thenReturn(user);
        when(jwtService.getToken(String.valueOf(user.getId()))).thenReturn("eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJjdXN0b21lcl9pZCJ9.7-uu9F8Odhj9bNGoYmvLfYLh4U-S_be5OfPROggDyOV3QhlAz6nSa3a5rYpyUOty");

        TokenDto token = authenticationService.login(credentials);

        assertEquals("eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJjdXN0b21lcl9pZCJ9.7-uu9F8Odhj9bNGoYmvLfYLh4U-S_be5OfPROggDyOV3QhlAz6nSa3a5rYpyUOty", token.getToken());

        verify(userRepository, times(1)).findByUsername(argThat(username -> username.equals(credentials.getUsername())));
    }

    @Test
    void testLogin_InvalidUsername() {
        CredentialsDto credentials = new CredentialsDto("customer-invalid", "customer123-PASSWORD");
        when(userRepository.findByUsername("customer-invalid")).thenReturn(null);

        ValidationException exception = assertThrows(ValidationException.class, () -> authenticationService.login(credentials));
        assertEquals("Invalid username or password", exception.getMessage());

        verify(userRepository, times(1)).findByUsername(argThat(username -> username.equals(credentials.getUsername())));
    }

    @Test
    void testLogin_InvalidPassword() {
        CredentialsDto credentials = new CredentialsDto("customer", "customer123-invalid");
        User user = Customer.builder()
                        .id(10)
                        .username("customer")
                        .password(JWTService.encryptPassword("customer123-PASSWORD"))
                        .lastName("Customer-LastName")
                        .firstName("Customer-FirstName")
                        .email("customer.mail@gmial.com")
                        .phoneNumber("0712345678")
                        .address("Customer-Address")
                        .build();
        when(userRepository.findByUsername(credentials.getUsername())).thenReturn(user);

        ValidationException exception = assertThrows(ValidationException.class, () -> authenticationService.login(credentials));
        assertEquals("Invalid username or password", exception.getMessage());

        verify(userRepository, times(1)).findByUsername(argThat(username -> username.equals(credentials.getUsername())));
    }

    @Test
    void testSignupCustomer_ValidInput() {
        CustomerDto customerInput = CustomerDto.builder()
                .username("customer")
                .password("customer123-PASSWORD")
                .lastName("Customer-LastName")
                .firstName("Customer-FirstName")
                .email("customer.mail@gmial.com")
                .phoneNumber("0712345678")
                .address("Customer-Address")
                .build();

        Customer mockCustomer = Customer.builder()
                .id(10)
                .username("customer")
                .password(JWTService.encryptPassword("customer123-PASSWORD"))
                .lastName("Customer-LastName")
                .firstName("Customer-FirstName")
                .email("customer.mail@gmial.com")
                .phoneNumber("0712345678")
                .address("Customer-Address")
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);

        Customer customer = authenticationService.signupCustomer(customerInput);

        assertEquals(10, customer.getId());
        assertEquals(customerInput.getUsername(), customer.getUsername());
        assertTrue(JWTService.isPasswordValid(customerInput.getPassword(), customer.getPassword()));
        assertEquals(customerInput.getLastName(), customer.getLastName());
        assertEquals(customerInput.getFirstName(), customer.getFirstName());
        assertEquals(customerInput.getEmail(), customer.getEmail());
        assertEquals(customerInput.getPhoneNumber(), customer.getPhoneNumber());
        assertEquals(customerInput.getAddress(), customer.getAddress());

        verify(customerRepository, times(1)).save(
                argThat(customerUser
                        -> customerUser.getUsername().equals(customerInput.getUsername())
                        && JWTService.isPasswordValid(customerInput.getPassword(), customerUser.getPassword())
                        && customerUser.getLastName().equals(customerInput.getLastName())
                        && customerUser.getFirstName().equals(customerInput.getFirstName())
                        && customerUser.getEmail().equals(customerInput.getEmail())
                        && customerUser.getPhoneNumber().equals(customerInput.getPhoneNumber())
                        && customerUser.getAddress().equals(customerInput.getAddress())
                )
        );
    }

    @Test
    void testSignupEmployee_ValidInput() {
        EmployeeDto employeeInput = EmployeeDto.builder()
                .username("employee")
                .password("employee123-PASSWORD")
                .lastName("Employee-LastName")
                .firstName("Employee-FirstName")
                .email("employee.mail@gmial.com")
                .phoneNumber("0712345678")
                .birthDate(LocalDate.of(1990, 1, 1))
                .CNP("1234567890123")
                .IDSeries("RL")
                .IDNumber("123456")
                .build();

        Employee mockEmployee = Employee.builder()
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

        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        Employee employee = authenticationService.signupEmployee(employeeInput);

        assertEquals(10, employee.getId());
        assertEquals(employeeInput.getUsername(), employee.getUsername());
        assertTrue(JWTService.isPasswordValid(employeeInput.getPassword(), employee.getPassword()));
        assertEquals(employeeInput.getLastName(), employee.getLastName());
        assertEquals(employeeInput.getFirstName(), employee.getFirstName());
        assertEquals(employeeInput.getEmail(), employee.getEmail());
        assertEquals(employeeInput.getPhoneNumber(), employee.getPhoneNumber());
        assertEquals(employeeInput.getBirthDate(), employee.getBirthDate());
        assertEquals(employeeInput.getCNP(), employee.getCNP());
        assertEquals(employeeInput.getIDSeries(), employee.getIDSeries());
        assertEquals(employeeInput.getIDNumber(), employee.getIDNumber());

        verify(employeeRepository, times(1)).save(
                argThat(employeeUser
                        -> employeeUser.getUsername().equals(employeeInput.getUsername())
                        && JWTService.isPasswordValid(employeeInput.getPassword(), employeeUser.getPassword())
                        && employeeUser.getLastName().equals(employeeInput.getLastName())
                        && employeeUser.getFirstName().equals(employeeInput.getFirstName())
                        && employeeUser.getEmail().equals(employeeInput.getEmail())
                        && employeeUser.getPhoneNumber().equals(employeeInput.getPhoneNumber())
                        && employeeUser.getBirthDate().equals(employeeInput.getBirthDate())
                        && employeeUser.getCNP().equals(employeeInput.getCNP())
                        && employeeUser.getIDSeries().equals(employeeInput.getIDSeries())
                        && employeeUser.getIDNumber().equals(employeeInput.getIDNumber())
                )
        );
    }

}
