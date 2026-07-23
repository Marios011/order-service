package com.restaurant.orderservice.service;


import com.restaurant.orderservice.dto.MenuItemDto;
import com.restaurant.orderservice.model.OrderEntity;
import com.restaurant.orderservice.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate,  RabbitTemplate rabbitTemplate) {

        this.orderRepository = orderRepository;
        this.restTemplate =  restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    public OrderEntity createOrder(OrderEntity order, Long menuItemId) {

        String url = "http://localhost:8080/api/products/" + menuItemId;

        try {
            MenuItemDto menuItem = restTemplate.getForObject(url, MenuItemDto.class);

            if (menuItem != null && menuItem.isAvailable()) {
                return orderRepository.save(order);
            }
            throw new RuntimeException("Menu item not found or not available");

        }catch(HttpClientErrorException.NotFound e){
            throw new RuntimeException("Product not found with id: " + menuItemId);
        }catch (Exception e){
            throw new RuntimeException("Error communicating with menu service: " + e.getMessage());
        }
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

}
