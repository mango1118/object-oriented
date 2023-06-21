package com.oo.service.impl;

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
         * @description: 设性质未有增加（0），题目不可以用于组卷
         */
        question.setEnabled(0);
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

}
