package cn.bupt.JoinMe.controller;

import cn.bupt.JoinMe.model.UserTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class ApiController {

    @GetMapping("hello")
    @ResponseBody
    public String Hello()
    {
        return "hello world!";
    }

    @GetMapping("user")
    @ResponseBody
    public UserTest getUser(UserTest user)
    {
        return user;
    }
}
