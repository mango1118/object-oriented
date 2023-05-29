package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.PaperGraph;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/28 22:43
 */
@Mapper
@TableName("t_paper_graph")
public interface PaperGraphDao extends BaseMapper<PaperGraph> {
}
