package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.PaperGraph;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/28 22:43
 */
@Mapper
@TableName("t_paper_graph")
public interface PaperGraphDao extends BaseMapper<PaperGraph> {

    @Select("SELECT * FROM t_paper_graph")
    List<Map<String,Object>> getAllNeededContent();
}
