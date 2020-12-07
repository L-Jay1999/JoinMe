package cn.bupt.joinme.controller;

import cn.bupt.joinme.dao.UserDao;
import cn.bupt.joinme.exception.BaseException;
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

@BaseResponse
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Value("${uploadImagePath}")
    private String uploadImagePath;

    @GetMapping("/create")
    @ResponseBody
    public ResponseResult tryCreateUser() {
        User res = userDao.getUser();
        if (res != null)
            throw new BaseException(ResponseType.COMMON_FAIL);
        else
            return new ResponseResult(ResponseType.SUCCESS);
    }

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

    @GetMapping("/")
    @ResponseBody
    public User getUser() {
        User res = userDao.getUser();
        if (res != null)
            return res;
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseResult updateUser(@RequestBody User user) {
        if (userDao.updateUser(user))
            return new ResponseResult(ResponseType.SUCCESS);
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getUserBasedOnUserId(@PathVariable(name = "id") Integer id) {
        User res = userDao.getUser();
        if (res != null) {
            User target = userDao.getUserBasedOnUserId(res, id);
            if (target != null)
                return target;
            else
                throw new BaseException(ResponseType.NO_PERMISSON);
        }
        else
            throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseResult uploadImage(@PathVariable MultipartFile file)
    {
         User res = userDao.getUser();
         if (res != null) {
            if (file.isEmpty())
                return new ResponseResult(ResponseType.COMMON_FAIL);
            String fileName = file.getOriginalFilename();
            String suffixName;
            if (fileName != null)
                suffixName = fileName.substring(fileName.lastIndexOf("."));
            else
                throw new BaseException(ResponseType.COMMON_FAIL);
            fileName = UUID.randomUUID() + suffixName;//图片名
            File dest = new File(uploadImagePath + "/" + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BaseException(ResponseType.COMMON_FAIL);
            }
            return new ResponseResult(ResponseType.SUCCESS);
         }
         else
             throw new BaseException(ResponseType.USER_NOT_LOGIN);
    }
}
