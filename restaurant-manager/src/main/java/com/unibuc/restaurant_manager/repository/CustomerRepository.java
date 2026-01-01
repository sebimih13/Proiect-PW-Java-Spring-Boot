package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<User, Integer> {

}
