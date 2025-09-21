package com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos;

import lombok.Data;

import java.util.Map;

@Data
public class MoldOverviewDTO {
    private int totalActiveMolds;
    private Map<String, Integer> statusDistribution;
    private Map<String, Integer> categoryDistribution;
    private Map<String, Integer> machineWorkload;
    private double averageCompletionTime;
    private Map<String, Double> deliveryPerformance;
}

