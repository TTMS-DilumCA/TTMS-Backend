package com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos;

import lombok.Data;
import java.util.Map;

@Data
public class DeliveryPerformanceDTO {
    private int totalOnTime;
    private int totalDelayed;
    private Map<String, CategoryDeliveryDTO> byCategory;
}

