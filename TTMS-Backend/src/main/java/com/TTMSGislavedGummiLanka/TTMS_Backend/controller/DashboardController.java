package com.TTMSGislavedGummiLanka.TTMS_Backend.controller;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.DashboardDTO;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.ToolCrafterDashboardDTO;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private DashboardService dashboardService; // Interface instead of implementation

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboardData());
    }

    @GetMapping("/tool-crafter/{toolCrafterId}")
    @PreAuthorize("hasAuthority('ROLE_MACHINE_OPERATOR_02')")
    public ResponseEntity<ToolCrafterDashboardDTO> getToolCrafterDashboard(
            @PathVariable String toolCrafterId) {
        try {
            return ResponseEntity.ok(dashboardService.getToolCrafterDashboard(toolCrafterId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}

