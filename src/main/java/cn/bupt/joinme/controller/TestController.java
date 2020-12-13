package cn.bupt.joinme.controller;

import cn.bupt.joinme.exception.BaseException;
import cn.bupt.joinme.response.BaseResponse;
import cn.bupt.joinme.response.ResponseResult;
import cn.bupt.joinme.share.ResponseType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@BaseResponse
@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {
    @Value("${uploadImagePath}")
    private String uploadImagePath;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseResult uploadImage(@RequestParam(value = "file") MultipartFile file)
    {
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
            file.transferTo(dest.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(ResponseType.FILE_TRANSFER_ERROR);
        }
        return new ResponseResult(ResponseType.SUCCESS);
    }

    @GetMapping(value = "/{imgUrl:[a-zA-Z0-9_.-]+}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable("imgUrl") String filename) throws IOException {

        String path = Paths.get(uploadImagePath, filename).toString();
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }
}
