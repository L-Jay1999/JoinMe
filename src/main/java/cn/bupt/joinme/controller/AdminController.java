package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.OrderDao;
import cn.bupt.joinme.dao.OrderRequestDao;
import cn.bupt.joinme.dao.UserDao;
import cn.bupt.joinme.model.OrderRequest;
import cn.bupt.joinme.model.User;
import cn.bupt.joinme.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@BaseResponse
@RestController
@RequestMapping(value = "admin")
public class AdminController {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderRequestDao orderRequestDao;

    @GetMapping("/user")
    @ResponseBody
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public User getUser(@PathVariable(name = "id") Integer id) {
        return userDao.getUser(id);
    }

    @GetMapping("/orderrequest")
    @ResponseBody
    public List<OrderRequest> getAllRequest() {
        return orderRequestDao.getAllRequest();
    }

    @GetMapping("/orderrequest/{id}")
    @ResponseBody
    public OrderRequest getRequest(@PathVariable(name = "id") Integer id) {
        return orderRequestDao.getOneOrderRequest(id);
    }
}
