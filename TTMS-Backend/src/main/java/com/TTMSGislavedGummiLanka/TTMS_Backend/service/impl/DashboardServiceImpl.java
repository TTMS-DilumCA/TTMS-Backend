package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.DashboardDTO;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.ToolCrafterDashboardDTO;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Process;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Tools;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.ProcessRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.ToolsRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ToolsRepo toolsRepo;


    @Autowired
    private ProcessRepo processRepo;

    @Override
    public DashboardDTO getDashboardData() {
        DashboardDTO dashboard = new DashboardDTO();

        // Get all processes
        List<Process> allProcesses = processRepo.findAll();

        // Calculate process summary
        DashboardDTO.ProcessSummary summary = new DashboardDTO.ProcessSummary();
        summary.setTotalProcesses(allProcesses.size());
        summary.setActiveProcesses(allProcesses.stream()
                .filter(p -> !"completed".equalsIgnoreCase(p.getStatus()))
                .count());
        summary.setCompletedProcesses(allProcesses.stream()
                .filter(p -> "completed".equalsIgnoreCase(p.getStatus()))
                .count());

        // Find last activity date
        allProcesses.stream()
                .map(p -> p.getFinishedAt() != null ? p.getFinishedAt() : p.getStartedAt())
                .max(Date::compareTo)
                .ifPresent(summary::setLastActivityDate);

        // Get recent activities
        List<DashboardDTO.RecentActivity> recentActivities = allProcesses.stream()
                .sorted(Comparator.comparing(p -> p.getFinishedAt() != null ?
                        p.getFinishedAt() : p.getStartedAt(), Comparator.reverseOrder()))
                .limit(5)
                .map(this::convertToRecentActivity)
                .collect(Collectors.toList());

        dashboard.setProcessSummary(summary);
        dashboard.setRecentActivities(recentActivities);

        return dashboard;
    }


    private DashboardDTO.RecentActivity convertToRecentActivity(Process process) {
        DashboardDTO.RecentActivity activity = new DashboardDTO.RecentActivity();
        activity.setProcessId(process.getId());
        activity.setMoldNo(process.getMoldNo());
        activity.setProcess(process.getProcess());
        activity.setSide(process.getSide());
        activity.setStatus(process.getStatus());
        activity.setActivityDate(process.getFinishedAt() != null ?
                process.getFinishedAt() : process.getStartedAt());
        activity.setOperatorEmail(process.getFinishedOperator() != null ?
                process.getFinishedOperator() : process.getStartedOperator());
        return activity;
    }

    @Override
    public ToolCrafterDashboardDTO getToolCrafterDashboard(String toolCrafterId) {
        ToolCrafterDashboardDTO dashboard = new ToolCrafterDashboardDTO();

        // Get all tools for the specific tool crafter
        List<Tools> allTools = toolsRepo.findByToolCrafterId(toolCrafterId);

        // Calculate tool summary
        ToolCrafterDashboardDTO.ToolSummary summary = new ToolCrafterDashboardDTO.ToolSummary();
        summary.setTotalTools(allTools.size());
        summary.setAcknowledgedTools(allTools.stream()
                .filter(t -> "acknowledged".equalsIgnoreCase(t.getStatus()))
                .count());
        summary.setCompletedTools(allTools.stream()
                .filter(t -> "completed".equalsIgnoreCase(t.getStatus()))
                .count());

        // Calculate total tool amount
        summary.setTotalToolAmount(allTools.stream()
                .mapToInt(Tools::getToolAmount)
                .sum());

        // Find last activity date
        allTools.stream()
                .map(Tools::getTimestamp)
                .max(LocalDateTime::compareTo)
                .ifPresent(summary::setLastActivityDate);

        // Get recent activities
        List<ToolCrafterDashboardDTO.RecentToolActivity> recentActivities = allTools.stream()
                .sorted(Comparator.comparing(Tools::getTimestamp).reversed())
                .limit(5)
                .map(this::convertToToolActivity)
                .collect(Collectors.toList());

        dashboard.setToolSummary(summary);
        dashboard.setRecentActivities(recentActivities);

        return dashboard;
    }

    private ToolCrafterDashboardDTO.RecentToolActivity convertToToolActivity(Tools tool) {
        ToolCrafterDashboardDTO.RecentToolActivity activity =
                new ToolCrafterDashboardDTO.RecentToolActivity();
        activity.setToolId(tool.getId());
        activity.setToolNo(tool.getToolNo());
        activity.setToolAmount(tool.getToolAmount());
        activity.setStatus(tool.getStatus());
        activity.setToolCrafterEmail(tool.getToolcrafterUsername());
        activity.setTimestamp(tool.getTimestamp());
        return activity;
    }



}