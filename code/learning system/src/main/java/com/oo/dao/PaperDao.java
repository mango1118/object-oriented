package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.Paper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 15:55
 */
@Mapper
@TableName("t_paper")
public interface PaperDao extends BaseMapper<Paper> {

    Integer selectIdByName(String name);
}
