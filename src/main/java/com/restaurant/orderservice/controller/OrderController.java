package com.restaurant.orderservice.controller;


import com.restaurant.orderservice.model.OrderEntity;
import com.restaurant.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping("/{menuItemId}")
    public OrderEntity createOrder(@RequestBody OrderEntity order,  @PathVariable Long menuItemId) {

        return orderService.createOrder(order,  menuItemId);
    }

    @GetMapping
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

}
