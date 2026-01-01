package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilizatorRepository extends JpaRepository<Utilizator, Integer> {

    Utilizator findByUsername(String username);

}
