package com.TTMSGislavedGummiLanka.TTMS_Backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ToolWithCrafterDTO {
    private String id;
    private String toolNo;
    private int toolAmount;
    private LocalDateTime timestamp;
    private String crafterFullName;
    private int crafterEpfNo;
    private String crafterProfileImageUrl;
    private String status;

}
