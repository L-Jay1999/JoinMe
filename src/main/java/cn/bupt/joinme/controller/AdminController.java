package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.*;
import cn.bupt.joinme.model.*;
import cn.bupt.joinme.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@BaseResponse
@RestController
@RequestMapping(value = "admin")
public class AdminController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderRequestDao orderRequestDao;
    @Autowired
    private OrderRequestDetailDao orderRequestDetailDao;
    @Autowired
    private IncomeDao incomeDao;

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

    @PostMapping("/detail")
    @ResponseBody
    public List<OrderRequestDetail> getDetail(@RequestBody SearchRequest searchRequest) {
        return orderRequestDetailDao.getRequestDetail(searchRequest);
    }

    @PostMapping("/income")
    @ResponseBody
    public List<IncomeSummary> getIncome(@RequestBody SearchRequest searchRequest) {
        return incomeDao.getIncome(searchRequest);
    }
}
