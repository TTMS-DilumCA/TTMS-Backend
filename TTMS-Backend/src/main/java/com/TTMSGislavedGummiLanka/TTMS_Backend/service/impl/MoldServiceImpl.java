package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Mold;
import com.TTMSGislavedGummiLanka.TTMS_Backend.exception.MoldNotFoundException;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.MoldRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.MoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.util.List;
import java.util.Optional;
import java.util.*;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.analysedtos.*;




@Service
public class MoldServiceImpl implements MoldService {

    @Autowired
    private MoldRepo moldRepo;

    @Override
    public List<Mold> getMolds() {
        return moldRepo.findAll();
    }


    @Override
    public List<Mold> getMoldsByYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        Date startDate = calendar.getTime();
        calendar.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        Date endDate = calendar.getTime();

        return moldRepo.findAllByCreatedDateBetween(startDate, endDate);
    }

    @Override
    public Mold addMold(Mold mold) {
        // Set default status as "Ongoing" for new molds
        mold.setStatus("Ongoing");
        // Set created date as current time
        mold.setCreatedDate(new Date());
        return moldRepo.save(mold);
    }

    @Override
    public Mold deleteMold(String id) {
        Optional<Mold> optionalMold = moldRepo.findById(id);
        if (optionalMold.isEmpty()) {
            throw new MoldNotFoundException(id);
        }
        Mold mold = optionalMold.get();
        moldRepo.delete(mold);
        return mold;
    }

    @Override
    public Mold updateMold(String id, Mold mold) {
        Optional<Mold> optionalMold = moldRepo.findById(id);
        if (optionalMold.isEmpty()) {
            throw new MoldNotFoundException(id);
        }
        Mold moldVar = optionalMold.get();
        moldVar.setMoldNo(mold.getMoldNo());
        moldVar.setDocumentNo(mold.getDocumentNo());
        moldVar.setCustomer(mold.getCustomer());
        moldVar.setShrinkageFactor(mold.getShrinkageFactor());
        moldVar.setPlateSize(mold.getPlateSize());
        moldVar.setPlateWeight(mold.getPlateWeight());
        moldVar.setInvestmentNo(mold.getInvestmentNo());
        moldVar.setDescription(mold.getDescription());


        // Add these lines to update the new fields
        moldVar.setMachine(mold.getMachine());
        moldVar.setItem(mold.getItem());
        moldVar.setCategory(mold.getCategory());
        moldVar.setTargetedDeliveryDate(mold.getTargetedDeliveryDate());
        return moldRepo.save(moldVar);
    }

    @Override
    public Mold completeMold(String id) {
        Optional<Mold> optionalMold = moldRepo.findById(id);
        if (optionalMold.isEmpty()) {
            throw new MoldNotFoundException(id);
        }

        Mold mold = optionalMold.get();
        mold.setStatus("Completed");
        mold.setCompletedDate(new Date());
        return moldRepo.save(mold);
    }

    //functions for analysing parts
    @Override
    public MoldStatisticsDTO getComprehensiveMoldStatistics(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        Date startDate = calendar.getTime();
        calendar.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        Date endDate = calendar.getTime();

        List<Mold> allCompletedMolds = moldRepo.findAllCompletedMoldsByYearBetween(startDate, endDate);

        MoldStatisticsDTO statistics = new MoldStatisticsDTO();
        statistics.setCategoryBreakdown(calculateCategoryBreakdown(startDate, endDate));
        statistics.setDeliveryPerformance(calculateDeliveryPerformance(allCompletedMolds));
        statistics.setMachinePerformance(calculateMachinePerformance(allCompletedMolds));
        statistics.setTotalMolds(allCompletedMolds.size());

        return statistics;
    }
    private CategoryBreakdownDTO calculateCategoryBreakdown(Date startDate, Date endDate) {
        CategoryBreakdownDTO breakdown = new CategoryBreakdownDTO();

        breakdown.setNewMolds(moldRepo.findCompletedMoldsByCategoryAndYearBetween(
                startDate, endDate, "New Mold").size());
        breakdown.setRenovateMolds(moldRepo.findCompletedMoldsByCategoryAndYearBetween(
                startDate, endDate, "Renovate Mold").size());
        breakdown.setModifyMolds(moldRepo.findCompletedMoldsByCategoryAndYearBetween(
                startDate, endDate, "Modify Mold").size());

        return breakdown;
    }

    private DeliveryPerformanceDTO calculateDeliveryPerformance(List<Mold> molds) {
        DeliveryPerformanceDTO performance = new DeliveryPerformanceDTO();
        Map<String, CategoryDeliveryDTO> categoryMap = new HashMap<>();

        int totalOnTime = 0;
        int totalDelayed = 0;

        for (Mold mold : molds) {
            boolean isOnTime = mold.getCompletedDate().before(mold.getTargetedDeliveryDate())
                    || mold.getCompletedDate().equals(mold.getTargetedDeliveryDate());

            CategoryDeliveryDTO categoryStats = categoryMap.computeIfAbsent(
                    mold.getCategory(), k -> new CategoryDeliveryDTO());

            if (isOnTime) {
                totalOnTime++;
                categoryStats.setOnTime(categoryStats.getOnTime() + 1);
            } else {
                totalDelayed++;
                categoryStats.setDelayed(categoryStats.getDelayed() + 1);
            }
        }

        performance.setTotalOnTime(totalOnTime);
        performance.setTotalDelayed(totalDelayed);
        performance.setByCategory(categoryMap);

        return performance;
    }

    private MachineDeliveryPerformanceDTO calculateMachinePerformance(List<Mold> molds) {
        MachineDeliveryPerformanceDTO performance = new MachineDeliveryPerformanceDTO();
        Map<String, CategoryDeliveryDTO> machineMap = new HashMap<>();

        for (Mold mold : molds) {
            boolean isOnTime = mold.getCompletedDate().before(mold.getTargetedDeliveryDate())
                    || mold.getCompletedDate().equals(mold.getTargetedDeliveryDate());

            CategoryDeliveryDTO machineStats = machineMap.computeIfAbsent(
                    mold.getMachine(), k -> new CategoryDeliveryDTO());

            if (isOnTime) {
                machineStats.setOnTime(machineStats.getOnTime() + 1);
            } else {
                machineStats.setDelayed(machineStats.getDelayed() + 1);
            }
        }

        performance.setByMachine(machineMap);
        return performance;
    }


}