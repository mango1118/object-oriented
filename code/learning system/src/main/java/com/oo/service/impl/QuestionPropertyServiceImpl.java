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
    @Autowired
    private QuestionDao questionDao;

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
     * @param questionVo
     * @return
     */
    @Override
    public boolean update(QuestionVo questionVo) {
        //感觉要加一个事务
        //处理数据
        Question question = new Question();
        QuestionProperty questionProperty = new QuestionProperty();
        questionProperty.setQuestionId(questionVo.getId());
        questionProperty.setAnswer(questionVo.getAnswer());
        questionProperty.setChapter(questionVo.getChapter());
        questionProperty.setDifficulty(questionVo.getDifficulty());
        questionProperty.setErrorPoint(questionVo.getErrorPoint());
        questionProperty.setErrorRate(questionVo.getErrorRate());
        questionProperty.setKnowledgePoint(questionVo.getKnowledgePoint());
        question.setContent(questionVo.getContent());
        question.setType(questionVo.getType());
        question.setEnabled(1);
        //匹配题目
        UpdateWrapper<Question> wrapperQ = new UpdateWrapper<>();
        wrapperQ.eq("id",questionVo.getId());
        //匹配该题目性质
        UpdateWrapper<QuestionProperty> wrapperQP = new UpdateWrapper<>();
        wrapperQP.eq("question_id",questionVo.getId());
        //如果是修改性质.则题目性质不为空
        boolean flag1,flag2;
        if(questionPropertyDao.selectOne(wrapperQP)!=null){
            flag1 = this.questionPropertyDao.update(questionProperty,wrapperQP)>0;
            flag2 = this.questionDao.update(question,wrapperQ)>0;
        }else{
            flag1 = this.questionPropertyDao.insert(questionProperty) > 0;
            flag2 = this.questionDao.update(question,wrapperQ) > 0;
        }
        return flag1&&flag2;
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
        System.out.println(iPage.getRecords());
        Page<QuestionVo> result = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        result.setRecords(iPage.getRecords());

        return result;
    }
}
