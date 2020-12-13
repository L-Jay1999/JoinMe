package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.UserDao;
import cn.bupt.joinme.exception.BaseException;
import cn.bupt.joinme.model.User;
import cn.bupt.joinme.response.BaseResponse;
import cn.bupt.joinme.response.ResponseResult;
import cn.bupt.joinme.share.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 * 负责处理与创建用户及与用户信息相关的请求
 */
@BaseResponse
@CrossOrigin
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserDao userDao;

    /**
     * 尝试创建新用户（判断用户登录状态）
     * @return 允许/不允许创建
     */
    @GetMapping("/create")
    @ResponseBody
    public ResponseResult tryCreateUser() {
        User res = userDao.getUser();
        if (res != null)
            throw new BaseException(ResponseType.COMMON_FAIL);
        else
            return new ResponseResult(ResponseType.SUCCESS);
    }

    /**
     * 创建新用户
     * @param user 新用户信息
     * @return 成功/异常信息
     */
    @PostMapping("/create")
    @ResponseBody
    public ResponseResult createUser(@RequestBody User user) {
        User res = userDao.getUser();
        if (res != null)
            throw new BaseException(ResponseType.COMMON_FAIL);
        if (userDao.createUser(user))
            return new ResponseResult(ResponseType.SUCCESS);
        else
            throw new BaseException(ResponseType.USER_ALREADY_EXIST);
    }

    /**
     * 获取当前用户的信息
     * @return 用户的信息
     */
    @GetMapping("/")
    @ResponseBody
    public User getUser() {
        User res = userDao.getUser();
        if (res != null)
            return res;
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 修改当前用户的信息
     * @param user 修改后的用户信息
     * @return 成功/异常信息
     */
    @PostMapping("/")
    @ResponseBody
    public ResponseResult updateUser(@RequestBody User user) {
        if (userDao.updateUser(user))
            return new ResponseResult(ResponseType.SUCCESS);
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    /**
     * 获取指定用户的信息（该用户必须已经响应至少一个当前用户的召集令）
     * @param id 用户id（userId）
     * @return 该用户的信息
     */
    @GetMapping("/{id}")
    @ResponseBody
    public User getUserBasedOnUserId(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            User user = userDao.getUser(id);
            if (user == null)
                throw new BaseException(ResponseType.RESOURCE_NOT_EXIST);

            boolean hasPermisson = userDao.getUserBasedOnUserId(res, id);
            if (hasPermisson)
                return user;
            else
                throw new BaseException(ResponseType.NO_PERMISSION);
        }
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }
}
