package com.example.Binarfud.service;

import com.example.Binarfud.model.ApiResponse;
import com.example.Binarfud.model.Merchant;
import com.example.Binarfud.repository.MerchantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MerchantService {
    private final MerchantRepository merchantRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantRepository) {

        this.merchantRepository = merchantRepository;
    }

    public ResponseEntity<?> getAllMerchants() {
        try {
            List<Merchant> merchants = merchantRepository.findAll();
            log.info("Merchants: {}", merchants);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Merchants retrieved successfully", merchants));
        } catch (Exception e) {
            log.error("Failed to get merchants", e);
            return ResponseEntity.badRequest().body(new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    public ResponseEntity<?> updateMerchant(UUID id, Merchant newMerchantData) {
        try {
            Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
            if (optionalMerchant.isPresent()) {
                Merchant existingMerchant = optionalMerchant.get();
                existingMerchant.setMerchantName(newMerchantData.getMerchantName());
                existingMerchant.setMerchantLocation(newMerchantData.getMerchantLocation());
                existingMerchant.setOpen(newMerchantData.isOpen());
                Merchant updatedMerchant = merchantRepository.save(existingMerchant);
                log.info("Merchant updated: {}", updatedMerchant);
                return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Merchant updated successfully", updatedMerchant));
            } else {
                log.warn("Merchant not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Failed to update merchant", e);
            return ResponseEntity.badRequest().body(new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    public ResponseEntity<?> searchMerchantsByLocation(String location) {
        try {
            List<Merchant> merchants = merchantRepository.findActiveMerchantsByLocation(location);
            if (merchants.isEmpty()) {
                log.warn("No merchants found in location: {}", location);
                return ResponseEntity.notFound().build();
            } else {
                log.info("Merchants found in location {}: {}", location, merchants);
                return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Merchants found in location " + location, merchants));
            }
        } catch (Exception e) {
            log.error("Failed to search merchants by location", e);
            return ResponseEntity.badRequest().body(new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    public ResponseEntity<?> getMerchantById(UUID id) {
        try {
            Optional<Merchant> merchant = merchantRepository.findById(id);
            if (merchant.isPresent()) {
                log.info("Merchant: {}", merchant.get());
                return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Merchant retrieved successfully", merchant.get()));
            } else {
                log.warn("Merchant not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Failed to get merchant", e);
            return ResponseEntity.badRequest().body(new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    public ResponseEntity<?> saveMerchant(Merchant merchant) {
        try {
            Merchant newMerchant = merchantRepository.save(merchant);
            log.info("Merchant saved: {}", newMerchant);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, "Merchant saved successfully", newMerchant));
        } catch (Exception e) {
            log.error("Failed to save merchant", e);
            return ResponseEntity.badRequest().body(new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    public ResponseEntity<?> deleteMerchant(UUID id) {
        try {
            merchantRepository.deleteById(id);
            log.info("Merchant deleted");
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Merchant deleted successfully", null));
        } catch (Exception e) {
            log.error("Failed to delete merchant", e);
            return ResponseEntity.badRequest().body(new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    public ResponseEntity<?> countMerchantsByLocation(String locationName) {
        try {
            Integer count = merchantRepository.countMerchantsByLocation(locationName);
            log.info("Count of merchants in location {}: {}", locationName, count);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Count of merchants in location " + locationName, count));
        } catch (Exception e) {
            log.error("Failed to count merchants by location", e);
            return ResponseEntity.badRequest().body(new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

}

