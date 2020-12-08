package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.*;
import cn.bupt.joinme.exception.BaseException;
import cn.bupt.joinme.model.*;
import cn.bupt.joinme.response.BaseResponse;
import cn.bupt.joinme.share.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AdminController
 * 负责处理管理员发出的请求
 */
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

    /**
     * 获得当前系统中所有用户的信息
     * @return 所有用户信息组成的列表
     */
    @GetMapping("/user")
    @ResponseBody
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    /**
     * 获取系统中指定用户的信息
     * @param id 用户id（userId）
     * @return 该用户的信息
     */
    @GetMapping("/user/{id}")
    @ResponseBody
    public User getUser(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser(id);
        if (res != null)
            return res;
        else
            throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);
    }

    /**
     * 获取系统中所有召集令请求的信息
     * @return 所有召集令请求组成的列表
     */
    @GetMapping("/orderrequest")
    @ResponseBody
    public List<OrderRequest> getAllRequest() {
        return orderRequestDao.getAllRequest();
    }

    /**
     * 获取系统中指定召集令请求的信息
     * @param id 召集令请求的id（requestId）
     * @return 该召集令请求的信息
     */
    @GetMapping("/orderrequest/{id}")
    @ResponseBody
    public OrderRequest getRequest(@PathVariable(name = "id") Integer id) {
        OrderRequest res = orderRequestDao.getOneOrderRequest(id);
        if (res != null)
            return res;
        else
            throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);
    }

    /**
     * 获取满足特定条件的召集令成功明细信息
     * @param searchRequest 查询请求，包含起止时间
     * @return 满足特定条件的召集令成功明细信息组成的列表
     */
    @PostMapping("/detail")
    @ResponseBody
    public List<OrderRequestDetail> getDetail(@RequestBody SearchRequest searchRequest) {
        return orderRequestDetailDao.getRequestDetail(searchRequest);
    }

    /**
     * 获取满足特定条件的中介收入信息
     * @param searchRequest 查询请求，包含起止时间、地点、召集令类型
     * @return 满足特定条件的中介收入信息组成的列表
     */
    @PostMapping("/income")
    @ResponseBody
    public List<IncomeSummary> getIncome(@RequestBody SearchRequest searchRequest) {
        return incomeDao.getIncome(searchRequest);
    }
}
