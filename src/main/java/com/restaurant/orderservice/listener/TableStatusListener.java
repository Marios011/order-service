package com.restaurant.orderservice.listener;


import com.restaurant.orderservice.service.OrderUpdateService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TableStatusListener {

    private final OrderUpdateService orderUpdateService;

    public TableStatusListener(OrderUpdateService orderUpdateService) {
        this.orderUpdateService = orderUpdateService;
    }

    @RabbitListener(queues = "table-status-queue")
    public void receiveMessage(String message){
        String[] parts= message.split(":");
        Long tableId = Long.parseLong(parts[0]);
        boolean isOccupied = Boolean.parseBoolean(parts[1]);

        orderUpdateService.processTableUpdate(tableId, isOccupied);
    }

}
