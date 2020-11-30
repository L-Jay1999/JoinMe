package cn.bupt.JoinMe.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String Hello()
    {
        return "hello world!";
    }
}
