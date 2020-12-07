package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.OrderDao;
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

@BaseResponse
@RestController
@RequestMapping(value = "order")
public class OrderController {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @GetMapping("/")
    @ResponseBody
    public List<Order> getOrder() {
        return orderDao.getOrder();
    }

    @PostMapping("/issue")
    @ResponseBody
    public ResponseResult issueOrder(@RequestBody Order order) {
        User res = userDao.getUser();
        if (res != null) {
            orderDao.issueOrder(res, order);
            return new ResponseResult(ResponseType.SUCCESS);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @GetMapping("/issued")
    @ResponseBody
    public List<Order> getIssuedOrder() {
        User res = userDao.getUser();
        if (res != null) {
            return orderDao.getIssuedOrder(res);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Order getOneOrder(@PathVariable(name = "id") Integer id) {
        return orderDao.getOneOrder(id);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseResult updateOneOrder(@PathVariable(name = "id") Integer id, @RequestBody Order order) {
        User res = userDao.getUser();
        if (res != null) {
            if (orderDao.updateOneOrder(res, id, order))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseResult deleteOneOrder(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            if (orderDao.deleteOneOrder(res, id))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @PostMapping("/{id}/request")
    @ResponseBody
    public ResponseResult acceptOrder(@PathVariable(name = "id") Integer id, @RequestBody OrderRequest orderRequest) {
        User res = userDao.getUser();
        if (res != null) {
            if (orderDao.acceptOrder(res, id, orderRequest))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.COMMON_FAIL);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @GetMapping("/{id}/request")
    @ResponseBody
    public ResponseResult checkAcceptOrder(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            if (orderDao.checkAcceptOrder(res, id))
                return new ResponseResult(ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMsg(), "YES");
            else
                return new ResponseResult(ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMsg(), "NO");
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @GetMapping("/getrequested")
    @ResponseBody
    public List<Order> getRequestOrder() {
        User res = userDao.getUser();
        if (res != null) {
            return orderDao.getRequestOrder(res);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

}
