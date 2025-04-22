package com.TTMSGislavedGummiLanka.TTMS_Backend.repo;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class CustomUserRepoImpl implements CustomUserRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updatePasswordByEmail(String email, String password) {
        Query query = new Query(Criteria.where("email").is(email));
        Update update = new Update().set("password", password);
        mongoTemplate.updateFirst(query, update, User.class);
    }
}