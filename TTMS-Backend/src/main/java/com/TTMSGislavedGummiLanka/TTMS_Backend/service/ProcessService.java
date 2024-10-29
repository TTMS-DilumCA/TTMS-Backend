package com.TTMSGislavedGummiLanka.TTMS_Backend.service;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Process;

import java.util.List;

public interface ProcessService {
    List<Process> getProcesses();
    Process addProcess(Process process);
    Process deleteProcess(String id);
    Process updateProcess(String id, Process process);
}