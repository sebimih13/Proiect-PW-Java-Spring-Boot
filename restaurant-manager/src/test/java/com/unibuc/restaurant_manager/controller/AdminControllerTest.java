package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.dto.*;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.model.Manager;
import com.unibuc.restaurant_manager.model.Restaurant;
import com.unibuc.restaurant_manager.service.AdminService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AdminService adminService;

    @MockitoBean
    private JWTService jwtService;

    @Test
    void addManager_ShouldReturnManager() throws Exception {
        AssignManagerDto dto = AssignManagerDto.builder()
                .employeeId(3)
                .restaurantId(5)
                .build();

        Restaurant restaurant = Restaurant.builder()
                .id(1)
                .name("Restaurant Test")
                .stars(4)
                .city("Bucharest")
                .address("Restaurant Address")
                .phoneNumber("0123456789")
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
                .restaurant(restaurant)
                .build();

        Mockito.when(adminService.addManager(any(AssignManagerDto.class))).thenReturn(manager);

        mockMvc.perform(put("/admin/manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(manager.getId()))
                .andExpect(jsonPath("$.username").value(manager.getUsername()))
                .andExpect(jsonPath("$.firstName").value(manager.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(manager.getLastName()))
                .andExpect(jsonPath("$.email").value(manager.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(manager.getPhoneNumber()));
    }

    @Test
    void addRestaurant_ShouldReturnRestaurant() throws Exception {
        RestaurantDto dto = RestaurantDto.builder()
                .name("Test Restaurant")
                .stars(5)
                .city("Bucharest")
                .address("Restaurant Address")
                .phoneNumber("0123456789")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .id(1)
                .name("Restaurant Test")
                .stars(4)
                .city("Bucharest")
                .address("Restaurant Address")
                .phoneNumber("0123456789")
                .build();

        Mockito.when(adminService.addRestaurant(any(RestaurantDto.class))).thenReturn(restaurant);

        mockMvc.perform(post("/admin/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurant.getId()))
                .andExpect(jsonPath("$.name").value(restaurant.getName()))
                .andExpect(jsonPath("$.stars").value(restaurant.getStars()))
                .andExpect(jsonPath("$.city").value(restaurant.getCity()))
                .andExpect(jsonPath("$.address").value(restaurant.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(restaurant.getPhoneNumber()));
    }

}
