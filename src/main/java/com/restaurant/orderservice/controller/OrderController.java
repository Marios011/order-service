package com.restaurant.orderservice.controller;


import com.restaurant.orderservice.model.OrderEntity;
import com.restaurant.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping("/{menuItemId}")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity order, @PathVariable Long menuItemId) {

        return ResponseEntity.ok(orderService.createOrder(order,  menuItemId));
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getAllOrders() {

        return ResponseEntity.ok(orderService.getAllOrders());
    }

}
