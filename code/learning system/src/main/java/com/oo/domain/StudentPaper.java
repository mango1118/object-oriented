package com.oo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 学生答卷类
 * @author: mango
 * @date: 2023/5/26 14:50
 */
@TableName(value = "t_student_paper")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentPaper {

    private Integer studentPaperId;

    private Integer studentId;

    private Integer papperId;

    private Integer score;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
