package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.Order;
import cn.bupt.joinme.model.OrderRequest;
import cn.bupt.joinme.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public boolean createUser(User user) {
        if (hasUser(user.getUserName()))
            return false;

        user.setUserId();
        user.setRegisterDate(new Date());
        user.setModifyDate(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        mongoTemplate.save(user);
        return true;
    }

    public List<User> getAllUser() {
        return mongoTemplate.findAll(User.class);
    }

    public User getUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof UserDetails) {
            UserDetails realUser = (UserDetails)user;
            return getUser(realUser.getUsername());
        }
        return null;
    }

    public boolean getUserBasedOnUserId(User user, Integer id) {
        Query query = new Query(Criteria.where("userId").is(user.getUserId()));
        List<Order> orders = mongoTemplate.find(query, Order.class);
        List<OrderRequest> requests= new ArrayList<>();
        for (Order o: orders) {
            query = new Query(Criteria.where("orderId").is(o.getOrderId()));
            List<OrderRequest> reqs = mongoTemplate.find(query, OrderRequest.class);
            requests.addAll(reqs);
        }
        boolean flag = false;
        for(OrderRequest or: requests)
            if (or.getUserId().equals(id)) {
                flag = true;
                break;
            }

        return flag;
    }

    public User getUser(Integer userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    public User getUser(String name) {
        Query query = new Query(Criteria.where("userName").is(name));
        return mongoTemplate.findOne(query, User.class);
    }

    public boolean updateUser(User user) {
        Object objectuser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (objectuser instanceof UserDetails) {
            UserDetails realUser = (UserDetails)objectuser;
            Query query = new Query(Criteria.where("userName").is(realUser.getUsername()));
            Update update = new Update().set("phoneNumber", user.getPhoneNumber()).set("modifyDate", new Date());
            if (user.getPassword() != null)
                update.set("password", new BCryptPasswordEncoder().encode(user.getPassword()));
            mongoTemplate.updateFirst(query, update, User.class);
            return true;
        }
        else
            return false;
    }

    private boolean hasUser(String name) {
        Query query = new Query(Criteria.where("userName").is(name));
        return mongoTemplate.exists(query, User.class);
    }

    private boolean hasUser(int userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.exists(query, User.class);
    }
}
