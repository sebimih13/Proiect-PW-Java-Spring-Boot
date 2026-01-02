package com.unibuc.restaurant_manager.aspect;

import com.unibuc.restaurant_manager.annotation.ManagerOnly;
import com.unibuc.restaurant_manager.exception.UnauthorizedAccessException;
import com.unibuc.restaurant_manager.model.*;
import com.unibuc.restaurant_manager.service.JWTService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleAuthorizationAspect {

    @Autowired
    private JWTService jwtService;

    @Before("@annotation(com.unibuc.restaurant_manager.annotation.CustomerOnly)")
    public void checkCustomerAuthorization() {
        checkAuthorization(Customer.class);
    }

    @Before("@annotation(com.unibuc.restaurant_manager.annotation.EmployeeOnly)")
    public void checkEmployeeAuthorization() {
        checkAuthorization(Employee.class);
    }

    @Before("@annotation(com.unibuc.restaurant_manager.annotation.ManagerOnly)")
    public void checkManagerAuthorization() {
        checkAuthorization(Manager.class);
    }

    @Before("@annotation(com.unibuc.restaurant_manager.annotation.CookOnly)")
    public void checkCookAuthorization() {
        checkAuthorization(Cook.class);
    }

    @Before("@annotation(com.unibuc.restaurant_manager.annotation.BartenderOnly)")
    public void checkBartenderAuthorization() {
        checkAuthorization(Bartender.class);
    }

    @Before("@annotation(com.unibuc.restaurant_manager.annotation.WaiterOnly)")
    public void checkWaiterAuthorization() {
        checkAuthorization(Waiter.class);
    }

    private void checkAuthorization(Class<?> clazz) {
        if (!clazz.isInstance(jwtService.getUser())) {
            throw new UnauthorizedAccessException();
        }
    }

}
