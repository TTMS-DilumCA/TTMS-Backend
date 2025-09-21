package com.TTMSGislavedGummiLanka.TTMS_Backend.controller;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos.*;
import com.TTMSGislavedGummiLanka.TTMS_Backend.enums.TimeRange;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.SystemOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/system-overview")
@CrossOrigin
public class SystemOverviewController {

    @Autowired
    private SystemOverviewService systemOverviewService;

    @GetMapping("/molds")
    public ResponseEntity<MoldOverviewDTO> getMoldMetrics(
            @RequestParam(required = false) TimeRange timeRange,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(systemOverviewService.getMoldMetrics(timeRange, startDate, endDate));
    }

    @GetMapping("/customers")
    public ResponseEntity<CustomerOverviewDTO> getCustomerMetrics(
            @RequestParam(required = false) TimeRange timeRange,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(systemOverviewService.getCustomerMetrics(timeRange, startDate, endDate));
    }

    @GetMapping("/processes")
    public ResponseEntity<ProcessOverviewDTO> getProcessMetrics(
            @RequestParam(required = false) TimeRange timeRange,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(systemOverviewService.getProcessMetrics(timeRange, startDate, endDate));
    }

    @GetMapping("/tools")
    public ResponseEntity<ToolOverviewDTO> getToolMetrics(
            @RequestParam(required = false) TimeRange timeRange,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(systemOverviewService.getToolMetrics(timeRange, startDate, endDate));
    }

    @GetMapping("/workforce")
    public ResponseEntity<WorkforceOverviewDTO> getWorkforceMetrics(
            @RequestParam(required = false) TimeRange timeRange,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(systemOverviewService.getWorkforceMetrics(timeRange, startDate, endDate));
    }
}