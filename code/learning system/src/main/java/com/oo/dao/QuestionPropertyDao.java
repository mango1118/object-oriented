package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.domain.Question;
import com.oo.domain.QuestionProperty;
import com.oo.domain.vo.QuestionVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 15:56
 */
@Mapper
@TableName("t_question_property")
public interface QuestionPropertyDao extends BaseMapper<QuestionProperty> {
    IPage<QuestionVo> selectPageVo(Page<?> page);
}
