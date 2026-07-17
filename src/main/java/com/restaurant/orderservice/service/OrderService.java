package com.restaurant.orderservice.service;


import com.restaurant.orderservice.model.OrderEntity;
import com.restaurant.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderEntity createOrder(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

}
