package com.example.Binarfud.service;

import com.example.Binarfud.model.OrderDetail;
import com.example.Binarfud.repository.OrderDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public ResponseEntity<?> getAllOrderDetails() {
        try {
            List<OrderDetail> orderDetails = orderDetailRepository.findAll();
            log.info("Order details: {}", orderDetails);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            log.error("Failed to get order details", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> getOrderDetailById(UUID id) {
        try {
            Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
            if (orderDetail.isPresent()) {
                log.info("Order detail: {}", orderDetail.get());
                return ResponseEntity.ok(orderDetail.get());
            } else {
                log.warn("Order detail not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Failed to get order detail", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> saveOrderDetail(OrderDetail orderDetail) {
        try {
            OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
            log.info("Order detail saved: {}", newOrderDetail);
            return ResponseEntity.ok(newOrderDetail);
        } catch (Exception e) {
            log.error("Failed to save order detail", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> deleteOrderDetail(UUID id) {
        try {
            orderDetailRepository.deleteById(id);
            log.info("Order detail deleted");
            return ResponseEntity.ok("Order detail deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete order detail", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
