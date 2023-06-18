package com.oo.service;

import com.oo.domain.*;
import com.oo.domain.vo.QuestionsPaper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description:学生服务类
 * @author: mango
 * @date: 2023/5/26 15:34
 */
@Transactional
public interface ManualComposeService {

    /**
     * 创建试卷，返回全部题目列表
     * @param paper
     * @return
     */
    public QuestionsPaper createPaper(Paper paper);


    /**
     * 添加试题
     * @param paperDTO
     * @return
     */
    boolean addQuestions(PaperDTO paperDTO);



}
