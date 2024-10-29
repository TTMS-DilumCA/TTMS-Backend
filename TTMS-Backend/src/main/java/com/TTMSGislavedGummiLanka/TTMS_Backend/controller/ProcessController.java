package com.TTMSGislavedGummiLanka.TTMS_Backend.controller;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Process;
import com.TTMSGislavedGummiLanka.TTMS_Backend.exception.ProcessNotFoundException;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping
    public List<Process> getProcesses() {
        return processService.getProcesses();
    }

    @PostMapping
    public Process insert(@RequestBody Process process) {
        return processService.addProcess(process);
    }

    @PutMapping("/{id}")
    public Process update(@PathVariable String id, @RequestBody Process process) {
        return processService.updateProcess(id, process);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            processService.deleteProcess(id);
            return ResponseEntity.ok("Process deleted successfully");
        } catch (ProcessNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}