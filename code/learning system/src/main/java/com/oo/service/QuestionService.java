package com.oo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.domain.Question;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:07
 */
@Transactional
public interface QuestionService {
    public boolean addQuestion(Question question);


    public boolean deleteQuestion(Integer id);

    public String upload(MultipartFile graph, String fileUploadPath) throws IOException;


    public Question getQuestionById(Integer questionId);
}
