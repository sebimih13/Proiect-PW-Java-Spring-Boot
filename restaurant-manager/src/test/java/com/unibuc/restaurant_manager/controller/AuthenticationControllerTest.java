package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.dto.CredentialsDto;
import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.dto.EmployeeDto;
import com.unibuc.restaurant_manager.dto.TokenDto;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.service.AuthenticationService;
import com.unibuc.restaurant_manager.service.JWTService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationService authenticationService;

    @MockitoBean
    private JWTService jwtService;

    @Test
    void login_ShouldReturnToken() throws Exception {
        CredentialsDto credentials = new CredentialsDto();
        credentials.setUsername("user");
        credentials.setPassword("password");

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken("fake-jwt-token");

        Mockito.when(authenticationService.login(any(CredentialsDto.class)))
                .thenReturn(tokenDto);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }

    @Test
    void signupCustomer_ShouldReturnCreatedCustomer() throws Exception {
        CustomerDto customerDto = CustomerDto.builder()
                .username("customer")
                .password(JWTService.encryptPassword("customer123-PASSWORD"))
                .lastName("Customer-LastName")
                .firstName("Customer-FirstName")
                .email("customer.mail@gmial.com")
                .phoneNumber("0712345678")
                .address("Customer-Address")
                .build();;

        Customer customer = Customer.builder()
                .id(10)
                .username("customer")
                .password(JWTService.encryptPassword("customer123-PASSWORD"))
                .lastName("Customer-LastName")
                .firstName("Customer-FirstName")
                .email("customer.mail@gmial.com")
                .phoneNumber("0712345678")
                .address("Customer-Address")
                .build();

        Mockito.when(authenticationService.signupCustomer(any(CustomerDto.class))).thenReturn(customer);

        mockMvc.perform(post("/auth/signup/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.username").value(customerDto.getUsername()))
                .andExpect(jsonPath("$.email").value(customerDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(customerDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customerDto.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(customerDto.getPhoneNumber()))
                .andExpect(jsonPath("$.address").value(customerDto.getAddress()));
    }

    @Test
    void signupEmployee_ShouldReturnEmployee() throws Exception {
        EmployeeDto employeeDto = EmployeeDto.builder()
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

        Mockito.when(authenticationService.signupEmployee(any(EmployeeDto.class))).thenReturn(employee);

        mockMvc.perform(post("/auth/signup/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(employeeDto.getUsername()))
                .andExpect(jsonPath("$.lastName").value(employeeDto.getLastName()))
                .andExpect(jsonPath("$.firstName").value(employeeDto.getFirstName()))
                .andExpect(jsonPath("$.email").value(employeeDto.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(employeeDto.getPhoneNumber()))
                .andExpect(jsonPath("$.birthDate").value(employeeDto.getBirthDate().toString()))
                .andExpect(jsonPath("$.CNP").value(employeeDto.getCNP()))
                .andExpect(jsonPath("$.IDSeries").value(employeeDto.getIDSeries()))
                .andExpect(jsonPath("$.IDNumber").value(employeeDto.getIDNumber()));
    }

}
