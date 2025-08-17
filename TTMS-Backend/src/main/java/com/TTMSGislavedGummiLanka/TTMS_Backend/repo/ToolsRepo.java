package com.TTMSGislavedGummiLanka.TTMS_Backend.repo;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Tools;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ToolsRepo extends MongoRepository<Tools, String> {
    List<Tools> findByToolCrafterId(String toolCrafterId, Sort sort);
    List<Tools> findByTimestampBetween(Date startDate, Date endDate);


}

