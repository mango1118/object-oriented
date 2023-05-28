package com.oo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @description:文件上传接口
 * @author: mango
 * @date: 2023/5/27 21:50
 */
@RestController
@RequestMapping("/file")
public class FilesController {

    @Value("${files.upload.path}")
    // 写在resources/application.yml中
    private String fileUploadPath;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {

//        System.out.println(fileUploadPath);
//        Files fff = new Files(fileUploadPath);
//        System.out.println(fff.getAbsoluteFile());

        File saveFile = new File(fileUploadPath);
        String savePath = saveFile.getAbsolutePath() + "\\";

        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        File uploadParentFile = new File(savePath);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        if(!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }

        String uuid = IdUtil.fastSimpleUUID();
        File uploadFile = new File(savePath + uuid + StrUtil.DOT + type) ;
        file.transferTo(uploadFile);


        return "";
    }



}
