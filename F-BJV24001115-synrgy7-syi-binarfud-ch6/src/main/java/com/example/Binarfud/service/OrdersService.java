package com.example.Binarfud.service;

import com.example.Binarfud.model.Orders;
import com.example.Binarfud.repository.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrdersService {
    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public ResponseEntity<?> getAllOrders() {
        try {
            List<Orders> orders = ordersRepository.findAll();
            log.info("Orders: {}", orders);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Failed to get orders", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> getOrderById(UUID id) {
        try {
            Optional<Orders> order = ordersRepository.findById(id);
            if (order.isPresent()) {
                log.info("Order: {}", order.get());
                return ResponseEntity.ok(order.get());
            } else {
                log.warn("Order not found");
                return ResponseEntity.notFound().build();
            }
            } catch (Exception e) {
            log.error("Failed to get order", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> saveOrder(Orders order) {
        try {
            Orders newOrder = ordersRepository.save(order);
            log.info("Order saved: {}", newOrder);
            return ResponseEntity.ok(newOrder);
        } catch (Exception e) {
            log.error("Failed to save order", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> deleteOrder(UUID id) {
        try {
            ordersRepository.deleteById(id);
            log.info("Order deleted");
            return ResponseEntity.ok("Order deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete order", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
