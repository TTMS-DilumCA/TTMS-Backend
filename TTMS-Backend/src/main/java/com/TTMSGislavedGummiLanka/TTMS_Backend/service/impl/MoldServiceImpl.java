package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Mold;
import com.TTMSGislavedGummiLanka.TTMS_Backend.exception.MoldNotFoundException;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.MoldRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.MoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoldServiceImpl implements MoldService {

    @Autowired
    private MoldRepo moldRepo;

    @Override
    public List<Mold> getMolds() {
        return moldRepo.findAll();
    }

    @Override
    public Mold addMold(Mold mold) {
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
        moldVar.setStatus(mold.getStatus());
        return moldRepo.save(moldVar);
    }
}