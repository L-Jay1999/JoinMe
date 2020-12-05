package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.UserDao;
import cn.bupt.joinme.dao.UserTestDao;
import cn.bupt.joinme.exception.BaseException;
import cn.bupt.joinme.model.User;
import cn.bupt.joinme.model.UserTest;
import cn.bupt.joinme.response.BaseResponse;
import cn.bupt.joinme.response.ResponseResult;
import cn.bupt.joinme.share.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@BaseResponse
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserTestDao userTestDao;
    @Autowired
    private UserDao userDao;

    // Test only
//    @GetMapping("hello")
//    @ResponseBody
//    public String Hello()
//    {
//        return "hello world!";
//    }

    // Test only
//    @GetMapping("userTest")
//    @ResponseBody
//    public UserTest getUser(UserTest user)
//    {
//        userTestDao.saveUserTest(user);
//        return user;
//    }

    @PostMapping("")
    @ResponseBody
    public ResponseResult createUser(@RequestBody User user) {
        if (userDao.createUser(user))
            return new ResponseResult(ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMsg(), null);
        else
            throw new BaseException(ResponseType.RESOURCES_ALREADY_EXIST);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getUser(@PathVariable(name = "id") int userId) {
        User res = userDao.getUser(userId);
        if (res != null)
            return res;
        else
            throw new BaseException(ResponseType.RESOURCES_NOT_EXIST);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseResult updateUser(@PathVariable(name = "id") int userId, @RequestBody User user) {
        if (userDao.updateUser(userId, user))
            return new ResponseResult(ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMsg(), null);
        else
            throw new BaseException(ResponseType.RESOURCES_NOT_EXIST);
    }


}
