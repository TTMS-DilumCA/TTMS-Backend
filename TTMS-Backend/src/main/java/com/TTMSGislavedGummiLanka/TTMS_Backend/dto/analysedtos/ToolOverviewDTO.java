package com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos;

import lombok.Data;

import java.util.Map;

@Data
public class ToolOverviewDTO {
    private Map<String, Integer> statusDistribution;
    private Map<String, Integer> toolUsage;
    private Map<String, Integer> crafterPerformance;
}


