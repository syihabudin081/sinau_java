package com.example.Binarfud.payload;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RevenueResponseDTO {
    public RevenueResponseDTO(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    private BigDecimal totalRevenue;
}
