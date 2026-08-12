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

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(@RequestBody CreateOrderRequest request) {

        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getAllOrders() {

        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<OrderEntity> payOrder(@PathVariable Long id, @RequestBody PaymentRequest request) {

        return ResponseEntity.ok(orderService.payOrder(id,request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {

        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}
