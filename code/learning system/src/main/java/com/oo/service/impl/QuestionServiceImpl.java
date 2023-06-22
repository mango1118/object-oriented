package com.oo.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.dao.QuestionDao;
import com.oo.dao.QuestionPropertyDao;
import com.oo.domain.Question;
import com.oo.domain.QuestionProperty;
import com.oo.domain.Student;
import com.oo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:09
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    public QuestionDao questionDao;


    /**
     * @description: 增加题目
     */
    @Override
    public boolean addQuestion(Question question){

        /**
         * @description: 设性质未有增加（0），题目不可以用于组卷;初始化deleted
         */
        question.setEnabled(0);
        question.setDeleted(0);
        return questionDao.insert(question) > 0;
    }


    /**
     * @description: 删除题目
     */
    @Override
    public boolean deleteQuestion(Integer id)
    {
        /**
         * @description: 以删除位作为题目是否删除，不可以再用于组卷等，但试卷原有题目可用
         */
        Question question = questionDao.selectById(id);
        if(question==null)
            return false;
        question.setDeleted(1);
        //System.out.println(question);
        return questionDao.updateById(question) > 0;
    }

    /**
     * @description: 将图片转为url保存
     */
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
        return url;
    }

//    /**
//     * @description: 保存主观题图片
//     */
//    public boolean addObjQuestion(String type, String content){
//        Question question = new Question();
//        question.setType(type);
//        question.setContent(content);
//        question.setEnabled(0);
//        return questionDao.insert(question) > 0;
//    }
}
