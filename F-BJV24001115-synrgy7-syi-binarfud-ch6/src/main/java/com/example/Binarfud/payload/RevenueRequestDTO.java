package com.example.Binarfud.payload;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class RevenueRequestDTO {
    private UUID merchantId;
    private Timestamp startDate;
    private Timestamp endDate;
}
