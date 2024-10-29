package com.TTMSGislavedGummiLanka.TTMS_Backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "molds")
public class Mold {
    @Id
    private String id; // MongoDB will automatically generate this field
    private String itemNo;
    private String moldNo;
    private String documentNo;
    private String customer;
    private String shrinkageFactor;
    private String plateSize;
    private String plateWeight;
    private String investmentNo;
    private String description;
}