package com.example.Binarfud.payload;


import lombok.Data;

@Data
public class MerchantCreateUpdateDTO {
    public String merchantName;
    public String merchantLocation;
    public Boolean open;


    public Boolean getMerchantIsOpen() {
        return open;
    }
    public void setMerchantIsOpen(Boolean open) {
        this.open = open;
    }
}
