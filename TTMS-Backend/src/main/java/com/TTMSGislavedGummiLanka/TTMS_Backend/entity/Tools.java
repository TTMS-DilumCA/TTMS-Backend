package com.TTMSGislavedGummiLanka.TTMS_Backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tools")
public class Tools {
    @Id
    private String id;
    private String toolNo;
    private int toolAmount;
    private String status;
    private String toolCrafterId;
    private String toolcrafterUsername;
    private LocalDateTime timestamp;
}
