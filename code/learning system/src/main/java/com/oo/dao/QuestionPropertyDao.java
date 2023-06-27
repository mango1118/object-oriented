package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.domain.QuestionProperty;
import com.oo.domain.QuestionSearchDTO;
import com.oo.domain.vo.QuestionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;

import java.util.List;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 15:56
 */
@Mapper
@TableName("t_question_property")
public interface QuestionPropertyDao extends BaseMapper<QuestionProperty> {

    IPage<QuestionVo> selectPageVo(Page<?> page);

   // IPage<QuestionVo> searchPageVo(@Param("page")Page<?> page,@Param("questionSearchDTO") QuestionSearchDTO questionSearchDTO);
    //List<QuestionVo> searchPageVo(@Param("questionSearchDTO") QuestionSearchDTO questionSearchDTO);
    IPage<QuestionVo> searchPageVo(@Param("questionSearchDTO") QuestionSearchDTO questionSearchDTO,Page<QuestionVo> page);

    List<QuestionVo> selectAllVo();

    void calculateErrorRate(@Param("questionId") int questionId);


    @Select("SELECT answer FROM t_question_property WHERE question_id = #{questionId}")
    String getAnswerById(@Param("questionId") Integer questionId);



}
