package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.UserTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserTestDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public void saveUserTest(UserTest userTest)
    {
        mongoTemplate.save(userTest);
    }
}
