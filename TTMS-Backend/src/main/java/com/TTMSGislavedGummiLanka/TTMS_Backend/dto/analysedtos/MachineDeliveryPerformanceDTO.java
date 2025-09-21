package com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos;

import lombok.Data;
import java.util.Map;
@Data
public class MachineDeliveryPerformanceDTO {
    private Map<String, CategoryDeliveryDTO> byMachine;
}

