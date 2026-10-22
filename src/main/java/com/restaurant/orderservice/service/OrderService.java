package com.restaurant.orderservice.service;


import com.restaurant.orderservice.dto.MenuItemDto;
import com.restaurant.orderservice.model.OrderEntity;
import com.restaurant.orderservice.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.restaurant.orderservice.dto.CreateOrderRequest;
import com.restaurant.orderservice.dto.CreateOrderItemRequest;
import com.restaurant.orderservice.model.OrderItem;
import java.util.ArrayList;

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

    public OrderEntity createOrder(CreateOrderRequest request) {

        if(request.getItems()==null || request.getItems().isEmpty()){
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        OrderEntity order = new OrderEntity();
        order.setTableId(request.getTableId());
        order.setStatus(request.getStatus());
        order.setPaymentMethod(request.getPaymentMethod());

        List<OrderItem> orderItems = new ArrayList<>();
        double total=0;

        for (CreateOrderItemRequest requestedItem : request.getItems()) {
            if (requestedItem.getMenuItemId() == null
                    || requestedItem.getQuantity() == null
                    || requestedItem.getQuantity() <= 0) {
                throw new IllegalArgumentException("Each item needs a product ID and positive quantity.");
            }

            String url = "http://menu-service:8080/api/products/"
                    + requestedItem.getMenuItemId();

            try {
                MenuItemDto menuItem = restTemplate.getForObject(url, MenuItemDto.class);

            } catch (HttpClientErrorException.NotFound e) {
                throw new RuntimeException("Product not found with id: " + menuItemId);
            } catch (Exception e) {
                throw new RuntimeException("Error communicating with menu service: " + e.getMessage());
            }

            if (menuItem == null || !menuItem.isAvailable()) {
                throw new RuntimeException("Menu item not found or not available");
            }

            OrderItem item = new OrderItem();
            item.setMenuItemId(product.getId());
            item.setQuantity(requestedItem.getQuantity());
            item.setNote(requestedItem.getNote());
            item.setOrder(order);

            items.add(item);
            total += product.getPrice() * requestedItem.getQuantity();

        }
        order.setItems(items);
        order.setTotal(total);

        OrderEntity savedOrder = orderRepository.save(order);
        rabbitTemplate.convertAndSend("order_exchange", "order_routing_key", savedOrder);
        return savedOrder;
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }


    public OrderEntity payOrder(Long orderId, String paymentMethod) {

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Here you can implement the payment logic based on the paymentMethod
        // For simplicity, we will just set the status to "PAID" and save the order

        order.setStatus("PAID");
        order.setPaymentMethod(paymentMethod);
        return orderRepository.save(order);
    }

}
