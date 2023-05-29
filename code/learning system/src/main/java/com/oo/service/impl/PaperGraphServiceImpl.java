package com.oo.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.oo.dao.FilesDao;
import com.oo.dao.PaperGraphDao;
import com.oo.domain.Files;
import com.oo.domain.PaperGraph;
import com.oo.service.PaperGraphService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/28 22:49
 */
@Service
public class PaperGraphServiceImpl implements PaperGraphService {

    @Resource
    private PaperGraphDao paperGraphDao;

    @Override
    public String upload(MultipartFile graph, String fileUploadPath) throws IOException {
        //拼接磁盘路径
        File saveFile = new File(fileUploadPath);
        String savePath = saveFile.getAbsolutePath() + "\\";

        //获取上传文件信息
        String originalFilename = graph.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = graph.getSize();

        //找到磁盘目录
        File uploadParentFile = new File(savePath);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }

        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        //存储到磁盘
        File uploadFile = new File(savePath + fileUuid);
        graph.transferTo(uploadFile);
        //存储到数据库
        String url = "http://localhost:9090/graph/" + fileUuid;

//        //存储实体类
//        Files savesFile = new Files();
//        savesFile.setName(originalFilename);
//        savesFile.setType(type);
//        savesFile.setSize(size / 1024);
//        savesFile.setUrl(url);
//        filesDao.insert(savesFile);

        //怎么存储学生信息呢？
        PaperGraph paperGraph = new PaperGraph();

        paperGraph.setUrl(url);

        return url;
    }
}
