package com.TTMSGislavedGummiLanka.TTMS_Backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "processes")
public class Process {

    @Id
    private String id; // MongoDB will automatically generate this field
    private String process;
    private String side;
    private String operator;
    private String mCounter;
    private String cuttingToolAmount;
    private String description;
}
