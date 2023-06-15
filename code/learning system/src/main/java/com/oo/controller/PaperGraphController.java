package com.oo.controller;

import cn.hutool.core.io.FileUtil;
import com.oo.service.PaperGraphService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/28 22:55
 */
@RestController
@RequestMapping("/graph")
public class PaperGraphController {

    @Value("${files.upload.path}")
    // 写在resources/application.yml中
    private String fileUploadPath;

    @Resource
    private PaperGraphService paperGraphService;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile graph) throws IOException {
        String url = paperGraphService.upload(graph, fileUploadPath);
        return url;
    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

}
