package com.TTMSGislavedGummiLanka.TTMS_Backend.service;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.CreateToolRequest;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.ToolWithCrafterDTO;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Tools;
import java.util.List;

public interface ToolsService {
    List<Tools> getAllTools();
    Tools getToolById(String id);
    Tools addTool(CreateToolRequest request);
    Tools updateTool(String id, Tools tool);
    void deleteTool(String id);
    List<ToolWithCrafterDTO> getToolWithCrafterDetails();
    Tools acknowledgeToolStatus(String id);
    List<Tools> getToolsByToolCrafterId(String toolCrafterId);

}