package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.CreateToolRequest;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.ToolWithCrafterDTO;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Tools;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.ToolsRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.UserRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToolsServiceImpl implements ToolsService {

    @Autowired
    private ToolsRepo toolsRepo;

    @Override
    public List<Tools> getAllTools() {
        return toolsRepo.findAll();
    }

    @Override
    public Tools getToolById(String id) {
        return toolsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found with id: " + id));
    }
    @Autowired
    private UserRepo userRepo;

    @Override
    public Tools addTool(CreateToolRequest request) {
        // Get the currently authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // Get the full user details from database
        User currentUser = userRepo.findByEmail(userDetails.getUsername())  // Use the instance variable
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Rest of your code...
        Tools tool = new Tools();
        tool.setToolNo(request.getToolNo());
        tool.setToolAmount(request.getToolAmount());
        tool.setStatus("completed");
        tool.setToolCrafterId(currentUser.getId());
        tool.setToolcrafterUsername(currentUser.getEmail());
        tool.setTimestamp(LocalDateTime.now());

        return toolsRepo.save(tool);
    }



    @Override
    public Tools updateTool(String id, Tools tool) {
        Tools existingTool = getToolById(id);
        tool.setId(existingTool.getId());
        tool.setTimestamp(LocalDateTime.now());
        return toolsRepo.save(tool);
    }

    @Override
    public void deleteTool(String id) {
        Tools tool = getToolById(id);
        toolsRepo.delete(tool);
    }

    @Override
    public List<ToolWithCrafterDTO> getToolWithCrafterDetails() {
        // Get the latest 50 tools, sorted by timestamp in descending order
        List<Tools> tools = toolsRepo.findAll(Sort.by(Sort.Direction.DESC, "timestamp"))
                .stream()
                .limit(50)
                .toList();

        // Create DTOs for each tool with crafter details
        return tools.stream()
                .map(tool -> {
                    User toolCrafter = userRepo.findById(tool.getToolCrafterId())
                            .orElseThrow(() -> new RuntimeException("Tool crafter not found with id: " + tool.getToolCrafterId()));

                    ToolWithCrafterDTO dto = new ToolWithCrafterDTO();
                    dto.setId(tool.getId());        // Add this line
                    dto.setToolNo(tool.getToolNo());
                    dto.setToolAmount(tool.getToolAmount());
                    dto.setTimestamp(tool.getTimestamp());
                    dto.setCrafterFullName(toolCrafter.getFullname());
                    dto.setCrafterEpfNo(toolCrafter.getEpfNo());
                    dto.setCrafterProfileImageUrl(toolCrafter.getProfileImageUrl());
                    dto.setStatus(tool.getStatus());

                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public Tools acknowledgeToolStatus(String id) {
        Tools tool = getToolById(id);
        tool.setStatus("acknowledged");

        return toolsRepo.save(tool);
    }
    @Override
    public List<Tools> getToolsByToolCrafterId(String toolCrafterId) {
        // Check if the tool crafter exists
        userRepo.findById(toolCrafterId)
                .orElseThrow(() -> new RuntimeException("Tool crafter not found with id: " + toolCrafterId));

        // Get all tools by this crafter, sorted by timestamp in descending order
        return toolsRepo.findByToolCrafterId(toolCrafterId,
                Sort.by(Sort.Direction.DESC, "timestamp"));
    }
}