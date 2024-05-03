package com.example.Binarfud.repository;

import com.example.Binarfud.model.ApiResponse;
import com.example.Binarfud.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

    @Query(value = "SELECT * FROM Merchant m WHERE m.merchant_location = ?1 AND m.deleted = false", nativeQuery = true)
    List<Merchant> findActiveMerchantsByLocation(String location);


    @Procedure(procedureName = "count_merchants_by_location", outputParameterName = "merchantCount")
    Integer countMerchantsByLocation(@Param("locationName") String locationName);


}
