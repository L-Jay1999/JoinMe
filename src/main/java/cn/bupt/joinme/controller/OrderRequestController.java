package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.OrderRequestDao;
import cn.bupt.joinme.dao.UserDao;
import cn.bupt.joinme.exception.BaseException;
import cn.bupt.joinme.model.OrderRequest;
import cn.bupt.joinme.model.User;
import cn.bupt.joinme.response.BaseResponse;
import cn.bupt.joinme.response.ResponseResult;
import cn.bupt.joinme.share.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@BaseResponse
@RestController
@RequestMapping(value = "orderrequest")
public class OrderRequestController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderRequestDao orderRequestDao;

    @GetMapping("/")
    @ResponseBody
    public List<OrderRequest> getOrderRequest() {
        User res = userDao.getUser();
        if (res != null)
            return orderRequestDao.getOrderRequest(res);
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @GetMapping("/orderid/{id}")
    @ResponseBody
    public List<OrderRequest> getOrderRequestBasedOnOrderId(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            List<OrderRequest> or = orderRequestDao.getOrderRequestBasedOnOrderId(res, id);
            if (or != null)
                return or;
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public OrderRequest getOneOrderRequest(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            OrderRequest or = orderRequestDao.getOneOrderRequest(res, id);
            if (or != null)
                return or;
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseResult updateOrderRequest(@PathVariable(name = "id") Integer id, @RequestBody OrderRequest orderRequest) {
        User res = userDao.getUser();
        if (res != null) {
            if (orderRequestDao.updateOrderRequest(res, id, orderRequest))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseResult deleteOrderRequest(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            if (orderRequestDao.deleteOrderRequest(res, id))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @PostMapping("/{id}/approve")
    @ResponseBody
    public ResponseResult approveOrderRequest(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            if (orderRequestDao.approveOrderRequest(res, id))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @PostMapping("/{id}/deny")
    @ResponseBody
    public ResponseResult denyOrderRequest(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            if (orderRequestDao.denyOrderRequest(res, id))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }
}
