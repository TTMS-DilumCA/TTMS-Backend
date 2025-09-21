package com.TTMSGislavedGummiLanka.TTMS_Backend.service;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos.*;
import com.TTMSGislavedGummiLanka.TTMS_Backend.enums.TimeRange;

import java.util.Date;

public interface SystemOverviewService {
    MoldOverviewDTO getMoldMetrics(TimeRange timeRange, Date startDate, Date endDate);
    CustomerOverviewDTO getCustomerMetrics(TimeRange timeRange, Date startDate, Date endDate);
    ProcessOverviewDTO getProcessMetrics(TimeRange timeRange, Date startDate, Date endDate);
    ToolOverviewDTO getToolMetrics(TimeRange timeRange, Date startDate, Date endDate);
    WorkforceOverviewDTO getWorkforceMetrics(TimeRange timeRange, Date startDate, Date endDate);
}

