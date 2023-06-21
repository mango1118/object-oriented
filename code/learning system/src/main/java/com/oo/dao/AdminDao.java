package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.Admin;
import com.oo.domain.Teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("t_admin")
public interface AdminDao extends BaseMapper<Admin> {
}
