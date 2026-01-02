package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.dto.PurchaseOrderDto;
import com.unibuc.restaurant_manager.mapper.CustomerMapper;
import com.unibuc.restaurant_manager.mapper.UserMapper;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.model.PurchaseOrder;
import com.unibuc.restaurant_manager.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public final class CustomerService extends UserService<Customer, CustomerDto> {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    protected JpaRepository<Customer, Integer> getRepository() {
        return customerRepository;
    }

    @Override
    protected String getUserEntityName() {
        return "Customer";
    }

    @Override
    protected UserMapper<Customer, CustomerDto> getMapper() {
        return customerMapper;
    }

    public List<PurchaseOrder> getMyOrders() {
        Customer customer = (Customer) jwtService.getUser();
        return customer.getOrders() != null ? customer.getOrders() : Collections.emptyList();
    }

    public PurchaseOrder addOrder(PurchaseOrderDto purchaseOrder) {
        Customer customer = (Customer) jwtService.getUser();
        purchaseOrder.setCustomerId(customer.getId());
        return purchaseOrderService.addOrder(purchaseOrder);
    }

}
