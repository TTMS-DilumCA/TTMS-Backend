package com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos;

import lombok.Data;

import java.util.Map;

@Data
public class ProcessOverviewDTO {
    private Map<String, Integer> statusDistribution;
    private Map<String, Long> averageCompletionTime;
    private Map<String, Integer> processTypeDistribution;
}

