package com.TTMSGislavedGummiLanka.TTMS_Backend.service;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.DashboardDTO;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.ToolCrafterDashboardDTO;

public interface DashboardService {
    DashboardDTO getDashboardData();
    ToolCrafterDashboardDTO getToolCrafterDashboard(String toolCrafterId);

}