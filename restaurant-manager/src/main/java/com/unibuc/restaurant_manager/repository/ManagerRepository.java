package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

}
