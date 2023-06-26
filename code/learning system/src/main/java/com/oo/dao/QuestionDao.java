package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 15:56
 */
@Mapper
@TableName("t_question")
public interface QuestionDao extends BaseMapper<Question> {

    @Select("SELECT question_type FROM question WHERE question_id = #{questionId}")
    String getQuestionTypeById(@Param("questionId") Integer questionId);
}
