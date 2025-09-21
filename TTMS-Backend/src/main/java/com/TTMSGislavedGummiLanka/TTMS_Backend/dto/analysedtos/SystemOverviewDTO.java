package com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos;

import lombok.Data;

@Data
public class SystemOverviewDTO {
    private MoldOverviewDTO moldMetrics;
    private CustomerOverviewDTO customerMetrics;
    private ProcessOverviewDTO processMetrics;
    private ToolOverviewDTO toolMetrics;
    private WorkforceOverviewDTO workforceMetrics;
}

