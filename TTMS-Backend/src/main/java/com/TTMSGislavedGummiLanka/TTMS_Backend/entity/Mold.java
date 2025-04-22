package com.TTMSGislavedGummiLanka.TTMS_Backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "molds")
public class Mold {
    @Id
    private String id; // MongoDB will automatically generate this field

    private String moldNo;
    private String documentNo;
    private String customer;
    private String shrinkageFactor;
    private String plateSize;
    private String plateWeight;
    private String investmentNo;
    private String description;
    private String status;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;

}