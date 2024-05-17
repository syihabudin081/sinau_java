package com.example.Binarfud.service;

import com.example.Binarfud.model.ApiResponse;
import com.example.Binarfud.model.Merchant;
import com.example.Binarfud.repository.MerchantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MerchantService {
    private final MerchantRepository merchantRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantRepository) {

        this.merchantRepository = merchantRepository;
    }

    public List<Merchant> getAllMerchants() {
        try {
            return merchantRepository.findAll();
        } catch (Exception e) {
            log.error("Failed to get merchants", e);
            return Collections.emptyList();
        }
    }


    public List<Object> updateMerchant(UUID id, Merchant newMerchantData) {
        try {
            log.info("Updating merchant with id: {}", id);
            log.info("New merchant data: {}", newMerchantData);
            Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
            if (optionalMerchant.isPresent()) {
                Merchant existingMerchant = optionalMerchant.get();
                existingMerchant.setMerchantName(newMerchantData.getMerchantName());
                existingMerchant.setMerchantLocation(newMerchantData.getMerchantLocation());
                existingMerchant.setOpen(newMerchantData.isOpen());
                Merchant updatedMerchant = merchantRepository.save(existingMerchant);
                log.info("Merchant updated: {}", updatedMerchant);
                List<Object> response = new ArrayList<>();
                response.add(updatedMerchant);
                return response;
            } else {
                log.warn("Merchant not found");
                return Collections.singletonList("Merchant not found");
            }
        } catch (Exception e) {
            log.error("Failed to update merchant", e);
            return Collections.singletonList(e.getMessage());
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

    public Optional getMerchantById(UUID id) {
        try {
            Optional<Merchant> merchant = merchantRepository.findById(id);
            if (merchant.isPresent()) {
                log.info("Merchant retrieved: {}", merchant.get());
                return merchant;
            } else {
                log.warn("Merchant not found");
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Failed to get merchant", e);
            return Optional.empty();
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

