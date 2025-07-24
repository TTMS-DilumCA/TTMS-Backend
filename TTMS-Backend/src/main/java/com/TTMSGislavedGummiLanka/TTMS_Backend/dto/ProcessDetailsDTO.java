package com.TTMSGislavedGummiLanka.TTMS_Backend.dto;

import lombok.Data;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Data
public class ProcessDetailsDTO {
    private String id;
    private String moldNo;
    private String mouldId;
    private String process;
    private String side;
    private String cuttingToolAmount;
    private String description;
    private String machine;
    private String status;
    private Date startedAt;
    private Date finishedAt;

    private String mcounter;
    
    // Operator details
    private OperatorDetails startedOperator;
    private OperatorDetails finishedOperator;
    
    // Mold details
    private MoldDetails mold;
    
    // Duration calculations
    private String duration;
    private long durationInMinutes;
    
    @Data
    public static class OperatorDetails {
        private String id;
        private String email;
        private String fullname;
        private Integer epfNo;
        private String profileImageUrl;
        private String role;
    }
    
    @Data
    public static class MoldDetails {
        private String id;
        private String moldNo;
        private String customer;
        private String plateSize;
        private String plateWeight;
        private String description;
        private String status;
        private String location;
        private String lastMaintenanceDate;
        private String nextMaintenanceDate;
    }
}
