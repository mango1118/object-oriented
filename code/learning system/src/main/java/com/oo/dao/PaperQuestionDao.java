package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.PaperQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 15:56
 */
@Mapper
@TableName("t_paper_question")
public interface PaperQuestionDao extends BaseMapper<PaperQuestion> {
    int addBatch(List<PaperQuestion> paperQuestions);
}
