package com.oo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oo.domain.StudentPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 15:56
 */
@Mapper
@TableName("t_student_paper")
public interface StudentPaperDao extends BaseMapper<StudentPaper> {
    @Update("UPDATE tstudent_paper SET total_score = #{objectiveScore} WHERE paper_id = #{paperId} AND student_id = #{studentId}")
    void updateTotalScore(@Param("paperId") Integer paperId, @Param("studentId") Integer studentId, @Param("objectiveScore") Integer objectiveScore);

    @Select("SELECT id FROM student_paper WHERE paper_id = #{paperId} AND student_id = #{studentId}")
    Integer getStudentPaperIdByPaperIdAndStudentId(Integer paperId, Integer studentId);

}
