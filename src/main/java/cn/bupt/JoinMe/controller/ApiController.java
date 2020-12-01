package cn.bupt.JoinMe.controller;

import cn.bupt.JoinMe.model.User;
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
    public User getUser(User user)
    {
        return user;
    }
}
