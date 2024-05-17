package com.example.Binarfud.payload;
import lombok.Data;

import java.util.UUID;

@Data
public class MerchantDTO {
private UUID id;
private String merchantName;
private String merchantLocation;
private boolean open;


}
