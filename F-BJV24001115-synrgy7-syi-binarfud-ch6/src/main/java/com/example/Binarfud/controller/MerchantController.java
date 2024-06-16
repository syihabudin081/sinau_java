package com.example.Binarfud.controller;

import com.example.Binarfud.model.ApiResponse;
import com.example.Binarfud.model.Merchant;
import com.example.Binarfud.payload.MerchantCreateUpdateDTO;
import com.example.Binarfud.payload.MerchantDTO;
import com.example.Binarfud.payload.RevenueRequestDTO;
import com.example.Binarfud.payload.RevenueResponseDTO;
import com.example.Binarfud.service.MerchantService;
import com.example.Binarfud.service.RevenueService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/merchants")
public class MerchantController {
    private final MerchantService merchantService;
    private final RevenueService revenueService;
    @Autowired
    private ModelMapper modelMapper;
    public MerchantController(MerchantService merchantService, RevenueService revenueService) {
        this.merchantService = merchantService;
        this.revenueService = revenueService;
    }

    @GetMapping
    public ResponseEntity<List<MerchantDTO>> getAllMerchants() {

        List<MerchantDTO> merchantDTOs = merchantService.getAllMerchants()
                .stream()
                .map(merchant -> modelMapper.map(merchant, MerchantDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(merchantDTOs);
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('MERCHANT')")
    public RevenueResponseDTO getRevenue(@RequestParam UUID merchantId,
                                         @RequestParam String startDateStr,
                                         @RequestParam String endDateStr) {
        // Buat objek RevenueRequestDTO dari parameter yang diterima
        Timestamp startDate = Timestamp.valueOf(startDateStr);
        Timestamp endDate = Timestamp.valueOf(endDateStr);
        log.info("Getting total revenue for merchant with id: " + merchantId);
        RevenueRequestDTO request = new RevenueRequestDTO();
        request.setMerchantId(merchantId);
        request.setStartDate(startDate);
        request.setEndDate(endDate);


        // Panggil metode getTotalRevenue() dari instance RevenueService
        return revenueService.getTotalRevenue(request);
    }

    @GetMapping("/search")
    public ResponseEntity searchMerchantsByLocation(@RequestParam String location) {
        return merchantService.searchMerchantsByLocation(location);
    }

    @GetMapping("/{id}")
    public ResponseEntity getMerchantById(@PathVariable UUID id) {
        try {
           Optional<MerchantDTO> response = merchantService.getMerchantById(id);
            if (response.isPresent()) {
                return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Merchant found", response.get())); // Menggunakan response.get() untuk mendapatkan nilai dari Optional
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND, "Merchant not found")); // Mengonstruksi ApiResponse dengan pesan "Merchant not found"
            }
        } catch (Exception e) {
            log.error("Failed to get merchant", e);
            return ResponseEntity.badRequest().body(new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }


    @PostMapping
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity createMerchant(@RequestBody MerchantCreateUpdateDTO merchantDTO) {
        Merchant merchant = modelMapper.map(merchantDTO, Merchant.class);
        return merchantService.saveMerchant(merchant);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<?> updateMerchant(@PathVariable UUID id, @RequestBody MerchantCreateUpdateDTO merchantDTO) {
        Optional<Merchant> existingMerchantOptional = merchantService.getMerchantById(id);

        if (existingMerchantOptional.isPresent()) {
            Merchant existingMerchant = existingMerchantOptional.get();

            if (merchantDTO.getMerchantName() != null) {
                existingMerchant.setMerchantName(merchantDTO.getMerchantName());
            }
            if (merchantDTO.getMerchantLocation() != null) {
                existingMerchant.setMerchantLocation(merchantDTO.getMerchantLocation());
            }
            if (merchantDTO.getMerchantIsOpen() != null) {
                existingMerchant.setOpen(merchantDTO.getMerchantIsOpen());
            }

            List<Object> updateResponse = merchantService.updateMerchant(id, existingMerchant);
            if (!updateResponse.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Merchant updated successfully", updateResponse));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to update merchant.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity deleteMerchant(@PathVariable UUID id) {
        return merchantService.deleteMerchant(id);
    }

    @GetMapping("/count-merchants")
    public ResponseEntity<?> countMerchantsByLocationAsJson(@RequestParam String locationName) {
        return merchantService.countMerchantsByLocation(locationName);
    }

}
