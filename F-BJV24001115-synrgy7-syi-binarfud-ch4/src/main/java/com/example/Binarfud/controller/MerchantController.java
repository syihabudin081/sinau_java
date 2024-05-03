package com.example.Binarfud.controller;

import com.example.Binarfud.model.Merchant;
import com.example.Binarfud.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/merchants")
public class MerchantController {
    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public ResponseEntity getAllMerchants() {
        return merchantService.getAllMerchants();
    }

    @GetMapping("/search")
    public ResponseEntity searchMerchantsByLocation(@RequestParam String location) {
        return merchantService.searchMerchantsByLocation(location);
    }

    @GetMapping("/{id}")
    public ResponseEntity getMerchantById(@PathVariable UUID id) {
        return merchantService.getMerchantById(id);
     }

    @PostMapping
    public ResponseEntity createMerchant(@RequestBody Merchant merchant) {
        return merchantService.saveMerchant(merchant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMerchant(@PathVariable UUID id) {
        return merchantService.deleteMerchant(id);
    }

    @GetMapping("/count-merchants")
    public ResponseEntity<?> countMerchantsByLocationAsJson(@RequestParam String locationName) {
        return merchantService.countMerchantsByLocation(locationName);
    }

}
