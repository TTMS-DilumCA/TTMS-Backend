package com.TTMSGislavedGummiLanka.TTMS_Backend.repo;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Process;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ProcessRepo extends MongoRepository<Process, String> {
    @Aggregation("{ $group: { _id: '$status', count: { $sum: 1 } } }")
    Map<String, Long> countByStatusGroup();

    @Aggregation({
            "{ $match: { timestamp: { $gte: ?0 } } }",
            "{ $group: { " +
                    "_id: { $dateToString: { format: '%Y-%m-%d', date: '$timestamp' } }, " +
                    "count: { $sum: 1 } " +
                    "} }",
            "{ $sort: { '_id': 1 } }"
    })
    List<Map<String, Object>> findDailyProcessStats(LocalDateTime startDate);
}