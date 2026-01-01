package com.unibuc.restaurant_manager.service;

import ch.qos.logback.core.net.server.Client;
import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.dto.CredentialsDto;
import com.unibuc.restaurant_manager.dto.EmployeeDto;
import com.unibuc.restaurant_manager.dto.TokenDto;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.model.User;
import com.unibuc.restaurant_manager.repository.CustomerRepository;
import com.unibuc.restaurant_manager.repository.EmployeeRepository;
import com.unibuc.restaurant_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JWTService jwtService;

    public TokenDto login(CredentialsDto credentials) {
        User user = userRepository.findByUsername(credentials.getUsername());

        if (user != null && JWTService.isPasswordValid(credentials.getPassword(), user.getPassword())) {
            return new TokenDto(jwtService.getToken(String.valueOf(user.getId())));
        }

        throw new ValidationException("Invalid username or password");
    }

    public Customer signupCustomer(CustomerDto customer) {
        Customer newCustomer = Customer.builder()
                .username(customer.getUsername())
                .password(JWTService.encryptPassword(customer.getPassword()))
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();

        customerRepository.save(newCustomer);
        return newCustomer;
    }

    public Employee signupEmployee(EmployeeDto employee) {
        Employee newEmployee = Employee.builder()
                .username(employee.getUsername())
                .password(JWTService.encryptPassword(employee.getPassword()))
                .email(employee.getEmail())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .phoneNumber(employee.getPhoneNumber())
                .birthDate(employee.getBirthDate())
                .CNP(employee.getCNP())
                .IDSeries(employee.getIDSeries())
                .IDNumber(employee.getIDNumber())
                .build();

        employeeRepository.save(newEmployee);
        return newEmployee;
    }

}
