package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.PurchaseOrderDto;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.model.*;
import com.unibuc.restaurant_manager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public final class PurchaseOrderService {

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

    public PurchaseOrder addOrder(PurchaseOrderDto purchaseOrderDto) {
        Customer customer = customerRepository.findById(purchaseOrderDto.getCustomerId())
                .orElseThrow(() -> new NotFoundException(String.format("Customer with id '%d' not found", purchaseOrderDto.getCustomerId())));

        Restaurant restaurant = restaurantRepository.findById(purchaseOrderDto.getRestaurantId())
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id '%d' not found", purchaseOrderDto.getRestaurantId())));

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .status(PurchaseOrderDto.Status.PENDING.toString())
                .data(LocalDate.now())
                .time(LocalTime.now())
                .customer(customer)
                .restaurant(restaurant)
                .build();

        PurchaseOrder savedOrder = purchaseOrderRepository.save(purchaseOrder);

        List<Contains> containsList = purchaseOrderDto.getProducts().stream()
                .map(p -> {
                    Product product = productRepository.findById(p.getProductId())
                            .orElseThrow(() -> new NotFoundException(String.format("Product with id '%d' not found: ", p.getProductId())));

                    Contains contains = Contains.builder()
                            .purchaseOrder(savedOrder)
                            .product(product)
                            .quantity(p.getQuantity())
                            .build();

                    return containsRepository.save(contains);
                })
                .toList();

        savedOrder.setProducts(containsList);

        return savedOrder;
    }

}
