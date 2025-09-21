package com.TTMSGislavedGummiLanka.TTMS_Backend.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ToolCrafterDashboardDTO {
    private ToolSummary toolSummary;
    private List<RecentToolActivity> recentActivities;

    @Data
    public static class ToolSummary {
        private long totalTools;
        private long acknowledgedTools;
        private long completedTools;
        private int totalToolAmount;
        private LocalDateTime lastActivityDate;
    }

    @Data
    public static class RecentToolActivity {
        private String toolId;
        private String toolNo;
        private int toolAmount;
        private String status;
        private String toolCrafterEmail;
        private LocalDateTime timestamp;
    }
}