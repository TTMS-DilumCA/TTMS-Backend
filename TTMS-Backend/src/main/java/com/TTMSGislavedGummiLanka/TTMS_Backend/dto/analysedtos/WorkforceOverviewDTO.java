package com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos;

import lombok.Data;

import java.util.Map;

@Data
public class WorkforceOverviewDTO {
    private Map<String, Integer> employeesByRole;
    private Map<String, Double> operatorEfficiency;
    private int totalEmployees;
}
