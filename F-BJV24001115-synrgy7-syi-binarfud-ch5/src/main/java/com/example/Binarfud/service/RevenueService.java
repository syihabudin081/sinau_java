package com.example.Binarfud.service;

import com.example.Binarfud.payload.RevenueRequestDTO;
import com.example.Binarfud.payload.RevenueResponseDTO;
import com.example.Binarfud.repository.MerchantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Slf4j
@Service
public class RevenueService {

    @Autowired
    private final MerchantRepository merchantRepository;

    public RevenueService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public RevenueResponseDTO getTotalRevenue(RevenueRequestDTO request) {
        UUID merchantId = request.getMerchantId();
        Timestamp startDate = request.getStartDate();
        Timestamp endDate = request.getEndDate();

        BigDecimal totalRevenue = merchantRepository.findTotalRevenueByMerchantIdAndDateRange(
                merchantId, startDate, endDate);
        return new RevenueResponseDTO(totalRevenue);
    }
}

