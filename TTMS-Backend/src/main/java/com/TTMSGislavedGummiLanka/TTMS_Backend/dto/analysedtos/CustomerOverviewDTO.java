package com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CustomerOverviewDTO {
    private int totalCustomers;
    private Map<String, Integer> customersByCompany;
    private List<TopCustomerDTO> topCustomers;
    private Map<String, Integer> geographicDistribution;
}

