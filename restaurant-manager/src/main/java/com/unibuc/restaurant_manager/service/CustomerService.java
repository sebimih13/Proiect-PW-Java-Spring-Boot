package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.*;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.mapper.CustomerMapper;
import com.unibuc.restaurant_manager.mapper.PurchaseOrderMapper;
import com.unibuc.restaurant_manager.model.*;
import com.unibuc.restaurant_manager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public final class CustomerService extends UserService<Customer, CustomerDto> {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ContainsRepository containsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Override
    protected JpaRepository<Customer, Integer> getRepository() {
        return customerRepository;
    }

    @Override
    protected String getUserEntityName() {
        return "Customer";
    }

    @Override
    protected CustomerMapper getMapper() {
        return customerMapper;
    }

    public List<PurchaseOrderResponseDto> getMyOrders() {
        Customer customer = (Customer) jwtService.getUser();

        if (customer.getOrders() == null || customer.getOrders().isEmpty()) {
            return Collections.emptyList();
        }

        return customer.getOrders().stream()
                .map(purchaseOrderMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public PurchaseOrderResponseDto addOrder(NewPurchaseOrderDto newPurchaseOrderDto) {
        Customer customer = (Customer) jwtService.getUser();

        Restaurant restaurant = restaurantRepository.findById(newPurchaseOrderDto.getRestaurantId())
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id '%d' not found", newPurchaseOrderDto.getRestaurantId())));

        Map<Integer, Integer> aggregatedProducts = new HashMap<>();
        for (ContainsDto dto : newPurchaseOrderDto.getProducts()) {
            validateProductAvailable(restaurant.getId(), dto.getProductId());
            aggregatedProducts.merge(dto.getProductId(), dto.getQuantity(), Integer::sum);
        }

        PurchaseOrder order = PurchaseOrder.builder()
                .status(PurchaseOrder.Status.PENDING.toString())
                .date(LocalDate.now())
                .time(LocalTime.now())
                .customer(customer)
                .restaurant(restaurant)
                .build();

        order = purchaseOrderRepository.save(order);

        List<Contains> products = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : aggregatedProducts.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new NotFoundException(String.format("Product with id '%d' not found", entry.getKey())));

            Contains contains = Contains.builder()
                    .id(Contains.ContainsId.builder()
                            .idProduct(product.getId())
                            .idPurchaseOrder(order.getId())
                            .build())
                    .product(product)
                    .purchaseOrder(order)
                    .quantity(entry.getValue())
                    .build();

            products.add(contains);
        }

        containsRepository.saveAll(products);
        order.setProducts(products);

        return purchaseOrderMapper.toResponseDto(order);
    }

    public PurchaseOrderResponseDto editOrder(Integer orderId, EditPurchaseOrderDto editPurchaseOrderDto) {
        Customer customer = (Customer) jwtService.getUser();

        PurchaseOrder order = purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id '%d' not found", orderId)));

        if (!order.getCustomer().getId().equals(customer.getId())) {
            throw new ValidationException("You can only edit your own orders");
        }

        if (!order.getStatus().equals(PurchaseOrder.Status.PENDING.toString())) {
            throw new ValidationException("Only pending orders can be edited");
        }

        Map<Integer, Integer> aggregatedProducts = new HashMap<>();
        for (ContainsDto dto : editPurchaseOrderDto.getProducts()) {
            validateProductAvailable(order.getRestaurant().getId(), dto.getProductId());
            aggregatedProducts.merge(dto.getProductId(), dto.getQuantity(), Integer::sum);
        }

        containsRepository.deleteAll(order.getProducts());

        List<Contains> products = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : aggregatedProducts.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new NotFoundException(String.format("Product with id '%d' not found", entry.getKey())));

            Contains contains = Contains.builder()
                    .id(Contains.ContainsId.builder()
                            .idProduct(product.getId())
                            .idPurchaseOrder(order.getId())
                            .build())
                    .product(product)
                    .purchaseOrder(order)
                    .quantity(entry.getValue())
                    .build();

            products.add(contains);
        }

        containsRepository.saveAll(products);
        order.setProducts(products);

        purchaseOrderRepository.save(order);
        return purchaseOrderMapper.toResponseDto(order);
    }

    public Map<String, String> removeOrder(Integer orderId) {
        Customer customer = (Customer) jwtService.getUser();

        PurchaseOrder order = purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id '%d' not found", orderId)));

        if (!order.getCustomer().getId().equals(customer.getId())) {
            throw new ValidationException("You can only remove your own orders");
        }

        if (!order.getStatus().equals(PurchaseOrder.Status.PENDING.toString())) {
            throw new ValidationException("Only pending orders can be removed");
        }

        containsRepository.deleteAll(order.getProducts());
        purchaseOrderRepository.delete(order);

        return Map.of("success", String.format("Order with id '%d' has been removed successfully", orderId));
    }

    private void validateProductAvailable(Integer restaurantId, Integer productId) {
        boolean isDishAvailable = dishRepository.dishAvailableInRestaurant(restaurantId, productId);
        boolean isDrinkAvailable = drinkRepository.drinkAvailableInRestaurant(restaurantId, productId);

        if (!isDishAvailable && !isDrinkAvailable) {
            throw new ValidationException(String.format("Product with id '%d' is not available in this restaurant", productId));
        }
    }

}
