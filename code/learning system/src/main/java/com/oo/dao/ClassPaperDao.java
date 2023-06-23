package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.ClassPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
@TableName("t_class_paper")
public interface ClassPaperDao extends BaseMapper<ClassPaper> {

    @Select("SELECT paper_id, paper_name, sent FROM t_class_paper WHERE class_id = #{classId}")
    List<Map<String, Object>> findExamPapersByClassId(Integer classId);
}
