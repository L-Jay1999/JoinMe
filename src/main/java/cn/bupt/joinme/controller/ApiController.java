package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.UserDao;
import cn.bupt.joinme.dao.UserTestDao;
import cn.bupt.joinme.model.User;
import cn.bupt.joinme.model.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    private UserTestDao userTestDao;
    @Autowired
    private UserDao userDao;

    // Test only
    @GetMapping("hello")
    @ResponseBody
    public String Hello()
    {
        return "hello world!";
    }

    // Test only
    @PostMapping("userTest")
    @ResponseBody
    public UserTest getUserTest(@RequestBody UserTest user)
    {
        userTestDao.saveUserTest(user);
        return user;
    }

    // TODO: how to create? use id and code?
    @PostMapping("createUser")
    @ResponseBody
    public boolean createUser(User user)
    {
        try{
            userDao.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @GetMapping("checkUser")
    @ResponseBody
    public boolean checkUser(String userId)
    {
        return userDao.hasUser(userId);
    }

    // TODO
    @PostMapping("updateInfo")
    @ResponseBody
    public void updateInfo(User user){}

    // TODO
    @GetMapping("checkPwd")
    @ResponseBody
    public void checkPwd(String password){}
}
