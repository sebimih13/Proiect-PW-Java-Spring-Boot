package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.ContainsDto;
import com.unibuc.restaurant_manager.dto.EditPurchaseOrderDto;
import com.unibuc.restaurant_manager.dto.NewPurchaseOrderDto;
import com.unibuc.restaurant_manager.dto.PurchaseOrderResponseDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.mapper.CustomerMapper;
import com.unibuc.restaurant_manager.mapper.PurchaseOrderMapper;
import com.unibuc.restaurant_manager.model.Customer;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private ContainsRepository containsRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DrinkRepository drinkRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private PurchaseOrderMapper purchaseOrderMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = Customer.builder()
                .id(1)
                .username("testuser")
                .password("password")
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .orders(new ArrayList<>())
                .build();

        restaurant = Restaurant.builder()
                .id(1)
                .name("Test Restaurant")
                .stars(4)
                .city("Test City")
                .address("Address")
                .phoneNumber("1234567890")
                .build();

        when(jwtService.getUser()).thenReturn(customer);
    }

    @Test
    void getMyOrders_WhenNoOrders_ShouldReturnEmptyList() {
        List<PurchaseOrderResponseDto> orders = customerService.getMyOrders();
        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }


    @Test
    void getMyOrders_WhenHasOrders_ShouldReturnOrders() {
        PurchaseOrder order = PurchaseOrder.builder()
                .id(1)
                .customer(customer)
                .restaurant(restaurant)
                .status(PurchaseOrder.Status.PENDING.toString())
                .date(LocalDate.now())
                .time(LocalTime.now())
                .build();

        customer.getOrders().add(order);

        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        when(purchaseOrderMapper.toResponseDto(order)).thenReturn(dto);

        List<PurchaseOrderResponseDto> orders = customerService.getMyOrders();
        assertEquals(1, orders.size());
        assertSame(dto, orders.get(0));
    }

    @Test
    void addOrder_WhenRestaurantNotFound_ShouldThrowNotFoundException() {
        NewPurchaseOrderDto newOrder = NewPurchaseOrderDto.builder()
                .restaurantId(2)
                .products(List.of())
                .build();

        when(restaurantRepository.findById(2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.addOrder(newOrder))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Restaurant with id '2' not found");
    }

    void addOrder_WhenProductNotAvailable_ShouldThrowValidationException() {
        NewPurchaseOrderDto newOrder = NewPurchaseOrderDto.builder()
                .restaurantId(1)
                .products(List.of(new ContainsDto(1, 2)))
                .build();

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));
        when(dishRepository.dishAvailableInRestaurant(1, 1)).thenReturn(false);
        when(drinkRepository.drinkAvailableInRestaurant(1, 1)).thenReturn(false);

        assertThatThrownBy(() -> customerService.addOrder(newOrder))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Product with id '1' is not available in this restaurant");
    }

    @Test
    void editOrder_WhenOrderNotFound_ShouldThrowNotFoundException() {
        EditPurchaseOrderDto editDto = new EditPurchaseOrderDto();
        when(purchaseOrderRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.editOrder(1, editDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Order with id '1' not found");
    }

    @Test
    void removeOrder_WhenOrderNotOwnedByCustomer_ShouldThrowValidationException() {
        Customer otherCustomer = Customer.builder().id(2).build();
        PurchaseOrder order = PurchaseOrder.builder()
                .id(1)
                .customer(otherCustomer)
                .status(PurchaseOrder.Status.PENDING.toString())
                .build();

        when(purchaseOrderRepository.findById(1)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> customerService.removeOrder(1))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("You can only remove your own orders");
    }

    @Test
    void removeOrder_WhenOrderPending_ShouldRemoveSuccessfully() {
        PurchaseOrder order = PurchaseOrder.builder()
                .id(1)
                .customer(customer)
                .status(PurchaseOrder.Status.PENDING.toString())
                .products(new ArrayList<>())
                .build();

        when(purchaseOrderRepository.findById(1)).thenReturn(Optional.of(order));

        Map<String, String> result = customerService.removeOrder(1);

        assertEquals("Order with id '1' has been removed successfully", result.get("success"));
        verify(containsRepository, times(1)).deleteAll(order.getProducts());
        verify(purchaseOrderRepository, times(1)).delete(order);
    }

}
