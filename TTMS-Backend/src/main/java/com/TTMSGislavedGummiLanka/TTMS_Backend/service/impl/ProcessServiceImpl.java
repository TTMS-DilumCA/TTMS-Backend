package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;

        import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Process;
        import com.TTMSGislavedGummiLanka.TTMS_Backend.exception.ProcessNotFoundException;
        import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.ProcessRepo;
        import com.TTMSGislavedGummiLanka.TTMS_Backend.service.ProcessService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.stereotype.Service;

        import java.util.Date;
        import java.util.List;
        import java.util.Optional;

        @Service
        public class ProcessServiceImpl implements ProcessService {

            @Autowired
            private ProcessRepo processRepo;

            @Override
            public List<Process> getProcesses() {
                return processRepo.findAll();
            }

            @Override
            public Process addProcess(Process process) {
                // Set status to "ongoing"
                process.setStatus("ongoing");
                // Set startedAt to current time
                process.setStartedAt(new Date());

                // Get logged-in user details
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    process.setStartedOperator(authentication.getName());
                    // If you have user ID in principal or details, extract it here
                    // Example: process.setStartedOperatorId(userId);
                }

                return processRepo.save(process);
            }

            @Override
            public Process deleteProcess(String id) {
                Optional<Process> optionalProcess = processRepo.findById(id);
                if (optionalProcess.isEmpty()) {
                    throw new ProcessNotFoundException(id);
                }
                Process process = optionalProcess.get();
                processRepo.delete(process);
                return process;
            }

            @Override
            public Process updateProcess(String id, Process process) {
                Optional<Process> optionalProcess = processRepo.findById(id);
                if (optionalProcess.isEmpty()) {
                    throw new ProcessNotFoundException(id);
                }
                Process processVar = optionalProcess.get();
                processVar.setProcess(process.getProcess());
                processVar.setSide(process.getSide());
                processVar.setMCounter(process.getMCounter());
                processVar.setCuttingToolAmount(process.getCuttingToolAmount());
                processVar.setDescription(process.getDescription());
                processVar.setMachine(process.getMachine());
                return processRepo.save(processVar);
            }



            @Override
            public Process finishProcess(String id) {
                Optional<Process> optionalProcess = processRepo.findById(id);
                if (optionalProcess.isEmpty()) {
                    throw new ProcessNotFoundException(id);
                }
                Process process = optionalProcess.get();
                process.setStatus("completed");
                process.setFinishedAt(new Date());
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    process.setFinishedOperator(authentication.getName());
                    // Set finishedOperatorId if available
                }
                return processRepo.save(process);
            }
        }