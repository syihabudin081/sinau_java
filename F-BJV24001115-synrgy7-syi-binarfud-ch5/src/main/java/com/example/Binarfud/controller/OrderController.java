package com.example.Binarfud.controller;

import com.example.Binarfud.model.Orders;
import com.example.Binarfud.service.InvoiceService;
import com.example.Binarfud.service.OrdersService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrdersService ordersService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/generate-invoice")
    public ResponseEntity<byte[]> generateInvoice(@RequestParam UUID orderId) {
        try {
            byte[] pdfBytes = invoiceService.generateOrderInvoice(orderId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "invoice.pdf");
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (JRException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @Autowired
    public OrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable UUID id) {
        return ordersService.getOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrder(@RequestBody Orders order) {
        return ordersService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable UUID id) {
        ordersService.deleteOrder(id);
    }
}
