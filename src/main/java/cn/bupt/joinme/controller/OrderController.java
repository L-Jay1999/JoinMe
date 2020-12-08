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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * OrderController
 * 负责处理发布、响应、修改、删除召集令及返回召集令的相关请求
 */
@BaseResponse
@RestController
@RequestMapping(value = "order")
public class OrderController {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Value("${uploadImagePath}")
    private String uploadImagePath;

    /**
     * 获取系统中的所有召集令信息
     * @return 系统中所有召集令信息组成的列表
     */
    @GetMapping("/")
    @ResponseBody
    public List<Order> getOrder() {
        return orderDao.getOrder();
    }

    /**
     * 发布召集令
     * @param order 需要发布的召集令信息
     * @return 成功/异常信息
     */
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

    /**
     * 获取当前用户已经发布的所有召集令的信息
     * @return 当前用户已经发布的所有召集令的信息组成的列表
     */
    @GetMapping("/issued")
    @ResponseBody
    public List<Order> getIssuedOrder() {
        User res = userDao.getUser();
        if (res != null) {
            return orderDao.getIssuedOrder(res);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 获取系统中指定的召集令信息
     * @param id 召集令id（orderId）
     * @return 该召集令的信息或错误信息
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Order getOneOrder(@PathVariable(name = "id") Integer id) {
        Order res = orderDao.getOneOrder(id);
        if (res != null)
            return res;
        else
            throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);
    }

    /**
     * 修改指定召集令的信息（须为当前用户发布的召集令）
     * @param id 召集令id（orderId）
     * @param order 修改后的召集令信息
     * @return 成功/异常信息
     */
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseResult updateOneOrder(@PathVariable(name = "id") Integer id, @RequestBody Order order) {
        User res = userDao.getUser();
        if (res != null) {
            Order oldOrder = orderDao.getOneOrder(id);
            if (oldOrder == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            if (orderDao.updateOneOrder(res, oldOrder, order))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 上传指定召集令的图片
     * @param id 召集令id（orderId）
     * @param file 文件
     * @return 成功/异常信息
     */
    @PostMapping("/{id}/upload")
    @ResponseBody
    public ResponseResult uploadImage(@PathVariable(name = "id") Integer id, @PathVariable MultipartFile file)
    {
        User res = userDao.getUser();
        if (res != null) {
            Order order = getOneOrder(id);
            if (order == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            if (!order.getUserId().equals(res.getUserId()))
                throw new BaseException(ResponseType.NO_PERMISSON);
            if (file.isEmpty())
                return new ResponseResult(ResponseType.COMMON_FAIL);

            String fileName = file.getOriginalFilename();
            String suffixName;
            if (fileName != null)
                suffixName = fileName.substring(fileName.lastIndexOf("."));
            else
                throw new BaseException(ResponseType.COMMON_FAIL);
            fileName = UUID.randomUUID() + suffixName;//图片名
            String destpath = uploadImagePath + "/" + fileName;
            File dest = new File(destpath);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BaseException(ResponseType.FILE_TRANSFER_ERROR);
            }

            String origin = order.getPicture();
            File originPic = new File(origin);
            if (originPic.exists())
                originPic.delete();
            order.setPicture(destpath);
            orderDao.updateOneOrder(order);
            return new ResponseResult(ResponseType.SUCCESS);
        }
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 删除（取消）指定召集令的信息（须为该用户发布的）
     * @param id 召集令id（orderId）
     * @return 成功/异常信息
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseResult deleteOneOrder(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            Order order = orderDao.getOneOrder(id);
            if (order == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            if (orderDao.deleteOneOrder(res, order))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 响应指定召集令
     * @param id 召集令id(orderId)
     * @param orderRequest 召集令请求信息
     * @return 成功/异常信息
     */
    @PostMapping("/{id}/request")
    @ResponseBody
    public ResponseResult acceptOrder(@PathVariable(name = "id") Integer id, @RequestBody OrderRequest orderRequest) {
        User res = userDao.getUser();
        if (res != null) {
            Order order = orderDao.getOneOrder(id);
            if (order == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            if (orderDao.acceptOrder(res, order, orderRequest))
                return new ResponseResult(ResponseType.SUCCESS);
            else
                throw new BaseException(ResponseType.COMMON_FAIL);
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 查看当前用户是否已响应某一召集令
     * @param id 召集令id(orderId)
     * @return 是/否
     */
    @GetMapping("/{id}/request")
    @ResponseBody
    public ResponseResult checkAcceptOrder(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            Order order = orderDao.getOneOrder(id);
            if (order == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            if (orderDao.checkAcceptOrder(res, id))
                return new ResponseResult(ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMsg(), "YES");
            else
                return new ResponseResult(ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMsg(), "NO");
        }
        throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 获取当前用户所有已经响应的召集令信息
     * @return 当前用户所有已经响应的召集令信息组成的列表
     */
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
