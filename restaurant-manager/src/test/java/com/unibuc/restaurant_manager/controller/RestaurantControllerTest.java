package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.model.Dish;
import com.unibuc.restaurant_manager.model.Drink;
import com.unibuc.restaurant_manager.model.Restaurant;
import com.unibuc.restaurant_manager.service.JWTService;
import com.unibuc.restaurant_manager.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantService restaurantService;

    @MockitoBean
    private JWTService jwtService;

    @Test
    void getRestaurantById_ShouldReturnRestaurant() throws Exception {
        Restaurant restaurant = Restaurant.builder()
                .id(1)
                .name("Restaurant Test")
                .stars(4)
                .city("Bucharest")
                .address("Restaurant Address")
                .phoneNumber("0123456789")
                .build();

        Mockito.when(restaurantService.getRestaurantById(1)).thenReturn(restaurant);

        mockMvc.perform(get("/restaurant")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurant.getId()))
                .andExpect(jsonPath("$.name").value(restaurant.getName()))
                .andExpect(jsonPath("$.stars").value(restaurant.getStars()))
                .andExpect(jsonPath("$.city").value(restaurant.getCity()))
                .andExpect(jsonPath("$.address").value(restaurant.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(restaurant.getPhoneNumber()));
    }

    @Test
    void getAllRestaurants_ShouldReturnListOfRestaurants() throws Exception {
        List<Restaurant> restaurants = Arrays.asList(
                Restaurant.builder()
                        .id(1)
                        .name("Restaurant Test")
                        .stars(4)
                        .city("Bucharest")
                        .address("Restaurant Address")
                        .phoneNumber("0123456789")
                        .build(),
                Restaurant.builder()
                        .id(2)
                        .name("Second Restaurant")
                        .stars(5)
                        .city("Cluj")
                        .address("Second Address")
                        .phoneNumber("0987654321")
                        .build()
        );

        Mockito.when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        mockMvc.perform(get("/restaurant/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(restaurants.size()))
                .andExpect(jsonPath("$[0].id").value(restaurants.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(restaurants.get(0).getName()))
                .andExpect(jsonPath("$[0].stars").value(restaurants.get(0).getStars()))
                .andExpect(jsonPath("$[0].city").value(restaurants.get(0).getCity()))
                .andExpect(jsonPath("$[0].address").value(restaurants.get(0).getAddress()))
                .andExpect(jsonPath("$[0].phoneNumber").value(restaurants.get(0).getPhoneNumber()))
                .andExpect(jsonPath("$[1].id").value(restaurants.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(restaurants.get(1).getName()))
                .andExpect(jsonPath("$[1].stars").value(restaurants.get(1).getStars()))
                .andExpect(jsonPath("$[1].city").value(restaurants.get(1).getCity()))
                .andExpect(jsonPath("$[1].address").value(restaurants.get(1).getAddress()))
                .andExpect(jsonPath("$[1].phoneNumber").value(restaurants.get(1).getPhoneNumber()));
    }

    @Test
    void getDishesByRestaurantId_ShouldReturnListOfDishes() throws Exception {
        List<Dish> dishes = Arrays.asList(
                Dish.builder().id(1).name("Dish1").price(10).build(),
                Dish.builder().id(2).name("Dish2").price(15).build()
        );

        Mockito.when(restaurantService.getDishesByRestaurantId(1)).thenReturn(dishes);

        mockMvc.perform(get("/restaurant/dishes")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(dishes.size()))
                .andExpect(jsonPath("$[0].name").value("Dish1"))
                .andExpect(jsonPath("$[1].name").value("Dish2"));
    }

    @Test
    void getDrinksByRestaurantId_ShouldReturnListOfDrinks() throws Exception {
        List<Drink> drinks = Arrays.asList(
                Drink.builder().id(1).name("Drink1").price(5).build(),
                Drink.builder().id(2).name("Drink2").price(7).build()
        );

        Mockito.when(restaurantService.getDrinksByRestaurantId(1)).thenReturn(drinks);

        mockMvc.perform(get("/restaurant/drinks")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(drinks.size()))
                .andExpect(jsonPath("$[0].name").value("Drink1"))
                .andExpect(jsonPath("$[1].name").value("Drink2"));
    }

}
