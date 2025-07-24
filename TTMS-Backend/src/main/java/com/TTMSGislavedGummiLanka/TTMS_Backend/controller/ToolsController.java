package com.TTMSGislavedGummiLanka.TTMS_Backend.controller;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.CreateToolRequest;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.ToolWithCrafterDTO;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Tools;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
public class ToolsController {

    @Autowired
    private ToolsService toolsService;


    @GetMapping
    public List<Tools> getAllTools() {
        return toolsService.getAllTools();
    }

    @GetMapping("/{id}")
    public Tools getToolById(@PathVariable String id) {
        return toolsService.getToolById(id);
    }



    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MACHINE_OPERATOR_02')")  // Adjust the role as needed
    public Tools addTool(@RequestBody CreateToolRequest request) {
        return toolsService.addTool(request);
    }



    @PutMapping("/{id}")
    public Tools updateTool(@PathVariable String id, @RequestBody Tools tool) {
        return toolsService.updateTool(id, tool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTool(@PathVariable String id) {
        toolsService.deleteTool(id);
        return ResponseEntity.ok("Tool deleted successfully");
    }

    @GetMapping("/with-crafter-details")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<ToolWithCrafterDTO>> getToolsWithCrafterDetails() {
        List<ToolWithCrafterDTO> dtos = toolsService.getToolWithCrafterDetails();
        return ResponseEntity.ok(dtos);
    }
    @PutMapping("/{id}/acknowledge")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Tools> acknowledgeToolStatus(@PathVariable String id) {
        Tools acknowledgedTool = toolsService.acknowledgeToolStatus(id);
        return ResponseEntity.ok(acknowledgedTool);
    }
    @GetMapping("/crafter/{crafterId}")
    @PreAuthorize("hasAuthority('ROLE_MACHINE_OPERATOR_02')")
    public ResponseEntity<List<Tools>> getToolsByToolCrafterId(@PathVariable String crafterId) {
        List<Tools> tools = toolsService.getToolsByToolCrafterId(crafterId);
        return ResponseEntity.ok(tools);
    }
}