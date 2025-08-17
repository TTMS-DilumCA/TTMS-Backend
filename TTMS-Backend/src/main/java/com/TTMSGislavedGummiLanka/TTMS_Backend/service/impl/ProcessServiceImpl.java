package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;

        import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.ProcessDetailsDTO;
        import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Mold;
        import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Process;
        import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
        import com.TTMSGislavedGummiLanka.TTMS_Backend.exception.ProcessNotFoundException;
        import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.MoldRepo;
        import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.ProcessRepo;
        import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.UserRepo;
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
            @Autowired
            private UserRepo userRepo;

            @Override
            public Process addProcess(Process process) {
                // Set status to "ongoing"
                process.setStatus("ongoing");
                // Set startedAt to current time
                process.setStartedAt(new Date());

                // Get logged-in user details
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    String email = authentication.getName();
                    process.setStartedOperator(email);

                    // Get user from repository using email instead of username
                    User user = userRepo.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    process.setStartedOperatorId(user.getId());
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
                    String email = authentication.getName();
                    process.setFinishedOperator(email);

                    // Get user from repository and set the ID
                    User user = userRepo.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    process.setFinishedOperatorId(user.getId());
                }

                return processRepo.save(process);
            }






            @Autowired
            private MoldRepo moldRepo;

            public ProcessDetailsDTO getProcessDetails(String processId) {
                Process process = processRepo.findById(processId)
                        .orElseThrow(() -> new ProcessNotFoundException("Process not found with id: " + processId));

                ProcessDetailsDTO dto = new ProcessDetailsDTO();
                // Set basic process details
                dto.setId(process.getId());
                dto.setMoldNo(process.getMoldNo());
                dto.setMouldId(process.getMouldId());
                dto.setProcess(process.getProcess());
                dto.setSide(process.getSide());
                dto.setCuttingToolAmount(process.getCuttingToolAmount());
                dto.setDescription(process.getDescription());
                dto.setStatus(process.getStatus());
                dto.setStartedAt(process.getStartedAt());
                dto.setFinishedAt(process.getFinishedAt());
                dto.setMcounter(process.getMCounter());

                // Set started operator details
                if (process.getStartedOperatorId() != null) {
                    User startedOperator = userRepo.findById(process.getStartedOperatorId()).orElse(null);
                    if (startedOperator != null) {
                        ProcessDetailsDTO.OperatorDetails startedOpDetails = new ProcessDetailsDTO.OperatorDetails();
                        startedOpDetails.setId(startedOperator.getId());
                        startedOpDetails.setEmail(startedOperator.getEmail());
                        startedOpDetails.setFullname(startedOperator.getFullname());
                        startedOpDetails.setEpfNo(startedOperator.getEpfNo());
                        startedOpDetails.setProfileImageUrl(startedOperator.getProfileImageUrl());
                        startedOpDetails.setRole(startedOperator.getRole().toString()); // Convert Role enum to String
                        dto.setStartedOperator(startedOpDetails);
                    }
                }

                // Set finished operator details
                if (process.getFinishedOperatorId() != null) {
                    User finishedOperator = userRepo.findById(process.getFinishedOperatorId()).orElse(null);
                    if (finishedOperator != null) {
                        ProcessDetailsDTO.OperatorDetails finishedOpDetails = new ProcessDetailsDTO.OperatorDetails();
                        finishedOpDetails.setId(finishedOperator.getId());
                        finishedOpDetails.setEmail(finishedOperator.getEmail());
                        finishedOpDetails.setFullname(finishedOperator.getFullname());
                        finishedOpDetails.setEpfNo(finishedOperator.getEpfNo());
                        finishedOpDetails.setProfileImageUrl(finishedOperator.getProfileImageUrl());
                        finishedOpDetails.setRole(finishedOperator.getRole().toString()); // Convert Role enum to String
                        dto.setFinishedOperator(finishedOpDetails);
                    }
                }

                // Set mold details
                if (process.getMoldNo() != null) {
                    Mold mold = moldRepo.findById(process.getMoldNo()).orElse(null);
                    if (mold != null) {
                        ProcessDetailsDTO.MoldDetails moldDetails = new ProcessDetailsDTO.MoldDetails();
                        moldDetails.setId(mold.getId());                     // Changed from getMoldNo
                        moldDetails.setMoldNo(mold.getMoldNo());
                        moldDetails.setCustomer(mold.getCustomer());
                        moldDetails.setPlateSize(mold.getPlateSize());
                        moldDetails.setPlateWeight(mold.getPlateWeight());
                        moldDetails.setDescription(mold.getDescription());
                        moldDetails.setStatus(mold.getStatus());
                        // Set additional fields if needed
                        dto.setMold(moldDetails);
                    }
                }


                // Calculate duration
                if (process.getStartedAt() != null && process.getFinishedAt() != null) {
                    long durationMillis = process.getFinishedAt().getTime() - process.getStartedAt().getTime();
                    long minutes = durationMillis / (60 * 1000);
                    long hours = minutes / 60;
                    long remainingMinutes = minutes % 60;
                    long seconds = (durationMillis / 1000) % 60;

                    String durationStr = String.format("%02d:%02d:%02d", hours, remainingMinutes, seconds);
                    dto.setDuration(durationStr);
                    dto.setDurationInMinutes(minutes);
                }

                return dto;


            }

        }