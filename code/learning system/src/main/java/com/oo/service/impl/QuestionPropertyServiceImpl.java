package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.dao.QuestionDao;
import com.oo.dao.QuestionPropertyDao;
import com.oo.domain.Question;
import com.oo.domain.QuestionProperty;
import com.oo.domain.vo.QuestionVo;
import com.oo.service.QuestionPropertyService;
import com.oo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:09
 */
@Service
public class QuestionPropertyServiceImpl implements QuestionPropertyService {

    @Autowired
    private QuestionPropertyDao questionPropertyDao;


    /**
     * 根据题目id,查询题目全部性质
     * @param id
     * @return
     */
    @Override
    public QuestionProperty selectById(Integer id) {
        QueryWrapper<QuestionProperty> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id",id);
        return questionPropertyDao.selectOne(wrapper);
    }

    /**
     * 修改某题目的性质
     * @param questionProperty
     * @return
     */
    @Override
    public boolean update(QuestionProperty questionProperty) {
        UpdateWrapper<QuestionProperty> wrapper = new UpdateWrapper<>();
        wrapper.eq("question_id",questionProperty.getQuestionId());

        return this.questionPropertyDao.update(questionProperty,wrapper) > 0;
    }

    /**
     * 设置某题目性质，保存
     * @param questionProperty
     * @return
     */
    @Override
    public boolean save(QuestionProperty questionProperty) {
        return questionPropertyDao.insert(questionProperty) > 0;
    }

    @Override
    public Page<QuestionVo> selectPageVo(Integer pageNum, Integer pageSize) {
        Page<QuestionVo> page = new Page<>(pageNum, pageSize);
        IPage<QuestionVo> iPage = questionPropertyDao.selectPageVo(page);
        Page<QuestionVo> result = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        result.setRecords(iPage.getRecords());
        return result;
    }
}
