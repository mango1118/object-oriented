package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: Mapper接口
 * @author: mango
 * @date: 2023/5/26 15:21
 */
@Mapper
@TableName("t_student")
public interface StudentDao extends BaseMapper<Student> {

    //获取班级且不重复按升序排列
    @Select("SELECT DISTINCT stu_class FROM t_student ORDER BY CAST(SUBSTRING(stu_class, 1, REGEXP_INSTR(stu_class, '[^0-9]')) AS UNSIGNED), stu_class")
    List<String> getAllClasses();

}
