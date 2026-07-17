package com.restaurant.orderservice.service;

import com.restaurant.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderUpdateService {

    private final OrderRepository orderRepository;

    public OrderUpdateService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void processTableUpdate(Long tableId, boolean isOccupied){
        if(!isOccupied){
            orderRepository.deleteByTableId(tableId);
        }
    }

}
