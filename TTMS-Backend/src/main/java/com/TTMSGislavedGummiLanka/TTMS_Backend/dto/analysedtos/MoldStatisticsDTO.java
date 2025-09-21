package com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos;

import lombok.Data;

@Data
public class MoldStatisticsDTO {
    private int totalMolds;
    private CategoryBreakdownDTO categoryBreakdown;
    private DeliveryPerformanceDTO deliveryPerformance;
    private MachineDeliveryPerformanceDTO machinePerformance;
}

