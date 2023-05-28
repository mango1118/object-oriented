package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.Files;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/28 11:35
 */
@Mapper
@TableName("t_file")
public interface FilesDao extends BaseMapper<Files> {
}
