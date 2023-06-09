package com.oo.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.dao.QuestionDao;
import com.oo.dao.QuestionPropertyDao;
import com.oo.domain.Question;
import com.oo.domain.QuestionProperty;
import com.oo.domain.QuestionSearchDTO;
import com.oo.domain.Student;
import com.oo.domain.vo.QuestionVo;
import com.oo.service.QuestionPropertyService;
import com.oo.service.QuestionService;
import com.sun.org.apache.xerces.internal.util.EntityResolverWrapper;
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
public class QuestionPropertyServiceImpl implements QuestionPropertyService {

    @Autowired
    private QuestionPropertyDao questionPropertyDao;
    @Autowired
    private QuestionDao questionDao;


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
     * 条件查询
     * @param pageNum
     * @param pageSize
     * @param questionId
     * @param type
     * @param knowledgePoint
     * @param chapter
     * @return
     */
    @Override
    public Map<String,Object> searchPageVo(Integer pageNum, Integer pageSize, String questionId, String type, String knowledgePoint, String chapter) {
        QuestionSearchDTO questionSearchDTO = new QuestionSearchDTO();
        if(null!=questionId && !"null".equals(questionId) && !questionId.equals("")){
            questionSearchDTO.setQuestionId(Integer.parseInt(questionId));
        }
        if(null!=chapter && !"null".equals(chapter.trim())){
            questionSearchDTO.setChapter(chapter.trim());
        }
        if(null!=type && !"null".equals(type.trim())){
            questionSearchDTO.setType(type.trim());
        }
        if(null!=knowledgePoint && !"null".equals(knowledgePoint.trim())){
            questionSearchDTO.setKnowledgePoint(knowledgePoint.trim());
        }

        questionSearchDTO.setOffset((pageNum-1)*pageSize);
        questionSearchDTO.setSize(pageSize);
        System.out.println(questionSearchDTO);
        //List<QuestionVo> data = questionPropertyDao.searchPageVo(questionSearchDTO);
        //int total = questionPropertyDao.selectAllVo().size();
        Page<QuestionVo> page = new Page<>(pageNum, pageSize);
        IPage<QuestionVo> iPage = questionPropertyDao.searchPageVo(questionSearchDTO,page);
        System.out.println("total:"+iPage.getTotal());
        System.out.println("size:"+iPage.getSize());
        //Page<QuestionVo> result = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        //result.setRecords(iPage.getRecords());
        HashMap<String, Object> res = new HashMap<>();
        res.put("data",iPage.getRecords());
        res.put("total",iPage.getTotal());
//        System.out.println(total);
//        System.out.println(data);
        return res;
    }


//    @Override
//    public Page<QuestionVo> selectPageVo(Integer pageNum, Integer pageSize) {
//        if(0 == pageSize || 0 == pageNum){
//            pageNum=1;
//            pageSize=500;
//        }
//        Page<QuestionVo> page = new Page<>(pageNum, pageSize);
//        IPage<QuestionVo> iPage = questionPropertyDao.selectPageVo(page);
//        System.out.println(iPage.getRecords());
//        Page<QuestionVo> result = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
//        result.setRecords(iPage.getRecords());
//
//        return result;
//    }



//    @Override
//    public List<QuestionVo> searchPageVo(QuestionSearchDTO questionSearchDTO) {
//        //System.out.println(questionSearchDTO);
//        List<QuestionVo> questionVoList = questionPropertyDao.searchPageVo(questionSearchDTO);
//        //System.out.println(questionVoList);
//        return questionVoList;
//    }


//    @Override
//    public Page<QuestionVo> searchPageVo(QuestionSearchDTO questionSearchDTO, Integer pageNum, Integer pageSize) {
//        Page<QuestionVo> page = new Page<>(pageNum, pageSize);
//        //Map<String,Object> map = new HashMap<>();
//        /**
//         *  questionId,
//         * type,
//         * knowledgePoint
//         * chapter);
//         */
////        map.put("questionId",questionSearchDTO.getQuestionId());
////        map.put("type",questionSearchDTO.getType());
////        map.put("knowledgePoint",questionSearchDTO.getKnowledgePoint());
////        map.put("chapter",questionSearchDTO.getChapter());
//
//
//        IPage<QuestionVo> iPage = questionPropertyDao.searchPageVo(page,questionSearchDTO);
//        System.out.println(iPage.getRecords());
//        Page<QuestionVo> result = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
//        result.setRecords(iPage.getRecords());
//
//        return result;
//    }
}
