package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos.*;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.*;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Process;
import com.TTMSGislavedGummiLanka.TTMS_Backend.enums.TimeRange;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.*;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.SystemOverviewService;
import com.TTMSGislavedGummiLanka.TTMS_Backend.util.DateRangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SystemOverviewServiceImpl implements SystemOverviewService {

    @Autowired
    private MoldRepo moldRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ProcessRepo processRepo;

    @Autowired
    private ToolsRepo toolsRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public MoldOverviewDTO getMoldMetrics(TimeRange timeRange, Date startDate, Date endDate) {
        Date[] dateRange = DateRangeUtil.calculateDateRange(timeRange, startDate, endDate);
        List<Mold> molds = moldRepo.findAllCompletedMoldsByYearBetween(dateRange[0], dateRange[1]);

        MoldOverviewDTO overview = new MoldOverviewDTO();
        overview.setTotalActiveMolds(molds.size());

        // Category distribution with Long to Integer conversion
        Map<String, Integer> categoryCount = molds.stream()
                .collect(Collectors.groupingBy(Mold::getCategory,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        overview.setCategoryDistribution(categoryCount);

        // Status distribution
        Map<String, Integer> statusDist = molds.stream()
                .collect(Collectors.groupingBy(Mold::getStatus,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        overview.setStatusDistribution(statusDist);

        // Machine workload
        Map<String, Integer> machineWork = molds.stream()
                .collect(Collectors.groupingBy(Mold::getMachine,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        overview.setMachineWorkload(machineWork);

        // Calculate average completion time
        double avgTime = molds.stream()
                .filter(m -> m.getCompletedDate() != null && m.getTargetedDeliveryDate() != null)
                .mapToLong(m -> m.getCompletedDate().getTime() - m.getTargetedDeliveryDate().getTime())
                .average()
                .orElse(0.0);
        overview.setAverageCompletionTime(avgTime);

        // Delivery performance
        Map<String, Double> deliveryPerf = new HashMap<>();
        molds.forEach(mold -> {
            String category = mold.getCategory();
            boolean isOnTime = !mold.getCompletedDate().after(mold.getTargetedDeliveryDate());
            deliveryPerf.merge(category, isOnTime ? 1.0 : 0.0, Double::sum);
        });
        overview.setDeliveryPerformance(deliveryPerf);

        return overview;
    }


    @Override
    public CustomerOverviewDTO getCustomerMetrics(TimeRange timeRange, Date startDate, Date endDate) {
        CustomerOverviewDTO overview = new CustomerOverviewDTO();

        List<Customer> customers = customerRepo.findAll();
        overview.setTotalCustomers(customers.size());

        // Customers by company
        Map<String, Integer> customersByCompany = customers.stream()
                .collect(Collectors.groupingBy(Customer::getCompany,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        overview.setCustomersByCompany(customersByCompany);

        // Top customers conversion to DTO
        List<TopCustomerDTO> topCustomersList = customers.stream()
                .limit(5)
                .map(customer -> {
                    TopCustomerDTO dto = new TopCustomerDTO();
                    // Set properties based on your TopCustomerDTO structure
                    return dto;
                })
                .collect(Collectors.toList());
        overview.setTopCustomers(topCustomersList);

        // Geographic distribution
//        Map<String, Integer> geoDistribution = customers.stream()
//                .collect(Collectors.groupingBy(Customer::getLocation,
//                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
//        overview.setGeographicDistribution(geoDistribution);

        return overview;
    }


    @Override
    public ProcessOverviewDTO getProcessMetrics(TimeRange timeRange, Date startDate, Date endDate) {
        Date[] dateRange = DateRangeUtil.calculateDateRange(timeRange, startDate, endDate);
        List<Process> processes = processRepo.findByStartedAtBetween(dateRange[0], dateRange[1]);

        ProcessOverviewDTO overview = new ProcessOverviewDTO();

        // Convert Long to Integer for status distribution
        Map<String, Integer> statusCount = processes.stream()
                .collect(Collectors.groupingBy(Process::getStatus,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        overview.setStatusDistribution(statusCount);

        // Keep Long type for average completion time as per DTO definition
        Map<String, Long> avgCompletionTime = processes.stream()
                .filter(p -> p.getStartedAt() != null && p.getFinishedAt() != null)
                .collect(Collectors.groupingBy(Process::getProcess,
                        Collectors.averagingLong(p ->
                                p.getFinishedAt().getTime() - p.getStartedAt().getTime())
                )).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Math.round(e.getValue())
                ));
        overview.setAverageCompletionTime(avgCompletionTime);

        // Add process type distribution
        Map<String, Integer> processTypes = processes.stream()
                .collect(Collectors.groupingBy(Process::getProcess,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        overview.setProcessTypeDistribution(processTypes);

        return overview;
    }

    @Override
    public ToolOverviewDTO getToolMetrics(TimeRange timeRange, Date startDate, Date endDate) {
        Date[] dateRange = DateRangeUtil.calculateDateRange(timeRange, startDate, endDate);
        List<Tools> tools = toolsRepo.findByTimestampBetween(dateRange[0], dateRange[1]);

        ToolOverviewDTO overview = new ToolOverviewDTO();

        // Convert Long to Integer for status distribution
        Map<String, Integer> statusCount = tools.stream()
                .collect(Collectors.groupingBy(Tools::getStatus,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        overview.setStatusDistribution(statusCount);

        // Tool usage - use appropriate field from Tools entity
        Map<String, Integer> toolUsage = tools.stream()
                .collect(Collectors.groupingBy(Tools::getToolcrafterUsername,
                        Collectors.summingInt(Tools::getToolAmount)));
        overview.setToolUsage(toolUsage);

        // Crafter performance
        Map<String, Integer> crafterPerf = tools.stream()
                .collect(Collectors.groupingBy(Tools::getToolcrafterUsername,
                        Collectors.summingInt(Tools::getToolAmount)));
        overview.setCrafterPerformance(crafterPerf);

        return overview;
    }


    @Override
    public WorkforceOverviewDTO getWorkforceMetrics(TimeRange timeRange, Date startDate, Date endDate) {
        WorkforceOverviewDTO overview = new WorkforceOverviewDTO();

        List<User> employees = userRepo.findAll();
        overview.setTotalEmployees(employees.size());

        // Convert Role to String and Long to Integer
        Map<String, Integer> employeesByRole = employees.stream()
                .collect(Collectors.groupingBy(user -> user.getRole().toString(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        overview.setEmployeesByRole(employeesByRole);

        // Calculate operator efficiency
        Map<String, Double> operatorEff = processRepo.findAll().stream()
                .filter(p -> p.getStatus().equals("completed"))
                .collect(Collectors.groupingBy(Process::getStartedOperator,
                        Collectors.averagingDouble(this::calculateEfficiency)));
        overview.setOperatorEfficiency(operatorEff);

        return overview;
    }
    private double calculateEfficiency(Process process) {
        if (process == null || process.getStartedAt() == null || process.getFinishedAt() == null) {
            return 0.0;
        }

        // 1. Time efficiency
        long processDuration = process.getFinishedAt().getTime() - process.getStartedAt().getTime();
        long processDurationHours = processDuration / (1000 * 60 * 60); // Convert to hours

        // Base expected duration (in hours) based on process type
        int expectedDuration;
        switch (process.getProcess().toLowerCase()) {
            case "cutting":
                expectedDuration = 2; // 2 hours for cutting
                break;
            case "milling":
                expectedDuration = 3; // 3 hours for milling
                break;
            default:
                expectedDuration = 4; // default duration
        }

        // 2. Tool usage efficiency
        double toolEfficiency = 1.0;
        try {
            int toolAmount = Integer.parseInt(process.getCuttingToolAmount());
            // Assume optimal tool usage is between 2-4 tools
            toolEfficiency = toolAmount >= 2 && toolAmount <= 4 ? 1.0 : 0.8;
        } catch (NumberFormatException e) {
            toolEfficiency = 0.5; // Default if tool amount is invalid
        }

        // 3. Time efficiency score (1.0 if completed within expected time, decreasing score if took longer)
        double timeEfficiency = Math.min(1.0, (double) expectedDuration / processDurationHours);

        // 4. Status completion factor
        double statusFactor = "completed".equalsIgnoreCase(process.getStatus()) ? 1.0 : 0.5;

        // Calculate final efficiency score (0.0 to 1.0 scale)
        double efficiency = (timeEfficiency * 0.4) + // 40% weight for time efficiency
                (toolEfficiency * 0.3) + // 30% weight for tool usage
                (statusFactor * 0.3);    // 30% weight for completion status

        // Round to 2 decimal places
        return Math.round(efficiency * 100.0) / 100.0;
    }

}