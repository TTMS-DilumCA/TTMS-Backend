package com.TTMSGislavedGummiLanka.TTMS_Backend.repo;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Mold;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Date;
import java.util.List;

public interface MoldRepo extends MongoRepository<Mold, String> {

    List<Mold> findAllByCreatedDateBetween(Date startDate, Date endDate);

    @Query("{'completedDate': {$gte: ?0, $lte: ?1}}")
    List<Mold> findAllCompletedMoldsByYearBetween(Date startDate, Date endDate);

    @Query("{'completedDate': {$gte: ?0, $lte: ?1}, 'category': ?2}")
    List<Mold> findCompletedMoldsByCategoryAndYearBetween(Date startDate, Date endDate, String category);

    @Query("{'completedDate': {$gte: ?0, $lte: ?1}, 'machine': ?2}")
    List<Mold> findCompletedMoldsByMachineAndYearBetween(Date startDate, Date endDate, String machine);
}

