package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class UserDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public boolean createUser(User user) {
        if (hasUser(user.getName()))
            return false;

        user.setUserId();
        user.setRegisterDate(new Date());
        mongoTemplate.save(user);
        return true;
    }

    public User getUser(int userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    public boolean updateUser(int userId, User user) {
        if (!hasUser(userId))
            return false;

        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update().set("phoneNumber", user.getPhoneNumber()).set("password", user.getPassword());
        mongoTemplate.updateFirst(query, update, User.class);
        return true;
    }

    private boolean hasUser(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.exists(query, User.class);
    }

    private boolean hasUser(int userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.exists(query, User.class);
    }
}
