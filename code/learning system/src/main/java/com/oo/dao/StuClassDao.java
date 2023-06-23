package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.StuClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@TableName("t_class")
public interface StuClassDao extends BaseMapper<StuClass> {
    @Select("SELECT id FROM t_class")
    List<Integer> getAllClassId();
}
