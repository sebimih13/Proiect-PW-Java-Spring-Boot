package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.Employee;
import com.unibuc.restaurant_manager.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByManager(Manager manager);

}
