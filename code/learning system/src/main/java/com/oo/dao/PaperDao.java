package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 15:55
 */
@Mapper
@TableName("t_paper")
public interface PaperDao extends BaseMapper<Paper> {

    //获取所有可用的试卷
    @Select("SELECT id, name FROM t_paper WHERE enabled = 1")
    List<Map<String,Object>> getAllExamPapers();
}
