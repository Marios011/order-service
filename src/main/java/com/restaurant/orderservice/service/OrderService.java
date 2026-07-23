package com.restaurant.orderservice.service;


import com.restaurant.orderservice.dto.MenuItemDto;
import com.restaurant.orderservice.model.OrderEntity;
import com.restaurant.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {

        this.orderRepository = orderRepository;
        this.restTemplate =  restTemplate;
    }

    public OrderEntity createOrder(OrderEntity order, Long menuItemId) {

        String url = "http://localhost:8080/api/products/" + menuItemId;
        MenuItemDto menuItem = restTemplate.getForObject(url, MenuItemDto.class);

        if (menuItem != null && menuItem.isAvailable()) {
            return orderRepository.save(order);
        }
        throw new RuntimeException("Menu item not found or not available");
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

}
