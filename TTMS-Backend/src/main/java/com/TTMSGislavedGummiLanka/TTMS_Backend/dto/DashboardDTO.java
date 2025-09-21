package com.TTMSGislavedGummiLanka.TTMS_Backend.dto;

import lombok.Data;
import java.util.List;
import java.util.Date;

@Data
public class DashboardDTO {
    private ProcessSummary processSummary;
    private List<RecentActivity> recentActivities;

    @Data
    public static class ProcessSummary {
        private long totalProcesses;
        private long activeProcesses;
        private long completedProcesses;
        private Date lastActivityDate;
    }

    @Data
    public static class RecentActivity {
        private String processId;
        private String moldNo;
        private String process;
        private String side;
        private String status;
        private Date activityDate;
        private String operatorEmail;
    }
}