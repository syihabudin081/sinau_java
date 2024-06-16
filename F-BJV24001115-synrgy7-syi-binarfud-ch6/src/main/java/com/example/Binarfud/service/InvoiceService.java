package com.example.Binarfud.service;
import com.example.Binarfud.model.OrderDetail;
import com.example.Binarfud.model.Orders;
import com.example.Binarfud.repository.OrderDetailRepository;
import com.example.Binarfud.repository.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
@Slf4j
public class InvoiceService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public byte[] generateOrderInvoice(UUID orderId) throws JRException {
        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderDetail> orderDetails = order.getOrderDetails();
        InputStream reportStream = getClass().getResourceAsStream("/reports/order_invoice.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orderId", order.getId());
        parameters.put("orderTime", order.getOrderTime());
        parameters.put("destinationAddress", order.getDestinationAddress());
        parameters.put("userName", order.getUser().getUsername());
        parameters.put("totalPrice", order.getOrderDetails());
        parameters.put("quantity", order.getOrderDetails());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderDetails);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
