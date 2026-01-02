package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.PurchaseOrder;
import com.unibuc.restaurant_manager.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    List<PurchaseOrder> findByRestaurant(Restaurant restaurant);

}
