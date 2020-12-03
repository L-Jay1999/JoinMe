package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public boolean createUser(User user)
    {
        try{
            mongoTemplate.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean hasUser(String userId)
    {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.exists(query, User.class);
    }
}
