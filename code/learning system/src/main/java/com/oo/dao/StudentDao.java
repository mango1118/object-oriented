package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: Mapper接口
 * @author: mango
 * @date: 2023/5/26 15:21
 */
@Mapper
@TableName("t_student")
public interface StudentDao extends BaseMapper<Student> {
}
