package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oo.dao.PaperQuestionDao;
import com.oo.dao.StudentDao;
import com.oo.domain.PaperQuestion;
import com.oo.service.PaperQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:08
 */
@Service
public class PaperQuestionServiceImpl implements PaperQuestionService {

    @Autowired
    //dao的使用方法请学习mybatis-plus
    private PaperQuestionDao paperQuestionDao;

    public List<Integer> getQuestionsByPaperId(Integer id){
        QueryWrapper<PaperQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_id", id).select("question_id");
        List<PaperQuestion> paperQuestions = paperQuestionDao.selectList(queryWrapper);
        List<Integer> questionIds = new ArrayList<>();
        for (PaperQuestion paperQuestion : paperQuestions) {
            questionIds.add(paperQuestion.getQuestionId());
        }
        return questionIds;
    }
}
