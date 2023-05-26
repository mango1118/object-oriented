package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.StudentPaper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 15:56
 */
@Mapper
@TableName("t_student_paper")
public interface StudentPaperDao extends BaseMapper<StudentPaper> {
}
