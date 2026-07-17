package com.restaurant.orderservice.repository;


import com.restaurant.orderservice.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    void deleteByTableId(Long orderId);
}
