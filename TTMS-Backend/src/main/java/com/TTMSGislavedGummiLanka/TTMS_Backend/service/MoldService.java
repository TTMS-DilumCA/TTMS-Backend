package com.TTMSGislavedGummiLanka.TTMS_Backend.service;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Mold;

import java.util.List;

public interface MoldService {

    List<Mold> getMolds();
    Mold addMold(Mold mold);
    Mold deleteMold(String id);
    Mold updateMold(String id, Mold mold);
}