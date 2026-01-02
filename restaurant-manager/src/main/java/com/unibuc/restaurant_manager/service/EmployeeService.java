package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.EmployeeDto;
import com.unibuc.restaurant_manager.mapper.EmployeeMapper;
import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public final class EmployeeService extends UserService<Employee, EmployeeDto>  {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    protected JpaRepository<Employee, Integer> getRepository() {
        return employeeRepository;
    }

    @Override
    protected String getUserEntityName() {
        return "Employee";
    }

    @Override
    protected EmployeeMapper getMapper() {
        return employeeMapper;
    }

}
