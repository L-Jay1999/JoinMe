package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.OrderDao;
import cn.bupt.joinme.dao.OrderRequestDao;
import cn.bupt.joinme.dao.UserDao;
import cn.bupt.joinme.exception.BaseException;
import cn.bupt.joinme.model.Order;
import cn.bupt.joinme.model.OrderRequest;
import cn.bupt.joinme.model.User;
import cn.bupt.joinme.response.BaseResponse;
import cn.bupt.joinme.response.ResponseResult;
import cn.bupt.joinme.share.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrderRequestController
 * 负责处理修改、删除召集令请求及返回召集令请求的相关请求
 */
@BaseResponse
@RestController
@RequestMapping(value = "orderrequest")
public class OrderRequestController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderRequestDao orderRequestDao;
    @Autowired
    private OrderDao orderDao;

    /**
     * 获取当前用户所有已经发布的召集令请求
     * @return 当前用户所有已经发布的召集令请求组成的列表
     */
    @GetMapping("/")
    @ResponseBody
    public List<OrderRequest> getOrderRequest() {
        User res = userDao.getUser();
        if (res != null)
            return orderRequestDao.getOrderRequest(res);
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 获取当前用户发布的某一召集令下的所有请求信息
     * @param id 召集令id（orderId）
     * @return 当前用户发布的该召集令下的所有请求信息组成的列表
     */
    @GetMapping("/orderid/{id}")
    @ResponseBody
    public List<OrderRequest> getOrderRequestBasedOnOrderId(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            Order order = orderDao.getOneOrder(id);
            if (order == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            List<OrderRequest> or = orderRequestDao.getOrderRequestBasedOnOrderId(res, order);
            if (or != null)
                return or;
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 获取某一召集令请求信息（须为该用户发布的）
     * @param id 召集令请求id（requestId）
     * @return 该召集令请求信息
     */
    @GetMapping("/{id}")
    @ResponseBody
    public OrderRequest getOneOrderRequest(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            OrderRequest or = orderRequestDao.getOneOrderRequest(id);
            if (or == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            if (!or.getUserId().equals(res.getUserId()))
                throw new BaseException(ResponseType.NO_PERMISSON);
            else
                return or;
        }
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 修改某一召集令请求信息（须为该用户发布的）
     * @param id 召集令请求id（requestId）
     * @param orderRequest 更新后的召集令请求信息
     * @return 成功/异常信息
     */
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseResult updateOrderRequest(@PathVariable(name = "id") Integer id, @RequestBody OrderRequest orderRequest) {
        User res = userDao.getUser();
        if (res != null) {
            OrderRequest or = orderRequestDao.getOneOrderRequest(id);
            if (or == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            if (orderRequestDao.updateOrderRequest(res, or, orderRequest))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 删除（取消）某一召集令请求（须为该用户发布的）
     * @param id 召集令请求id（requestId）
     * @return 成功/异常信息
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseResult deleteOrderRequest(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            OrderRequest or = orderRequestDao.getOneOrderRequest(id);
            if (or == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            if (orderRequestDao.deleteOrderRequest(res, or))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 同意某个召集令请求
     * @param id 召集令请求id（requestId）
     * @return 成功/异常信息
     */
    @PostMapping("/{id}/approve")
    @ResponseBody
    public ResponseResult approveOrderRequest(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            OrderRequest or = orderRequestDao.getOneOrderRequest(id);
            if (or == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            if (orderRequestDao.approveOrderRequest(res, or))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 拒绝某个召集令请求
     * @param id 召集令请求id（requestId）
     * @return 成功/异常信息
     */
    @PostMapping("/{id}/deny")
    @ResponseBody
    public ResponseResult denyOrderRequest(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            OrderRequest or = orderRequestDao.getOneOrderRequest(id);
            if (or == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            if (orderRequestDao.denyOrderRequest(res, or))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }
}
