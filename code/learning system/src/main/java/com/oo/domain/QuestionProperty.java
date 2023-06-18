package com.oo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 题目性质类
 * @author: mango
 * @date: 2023/5/26 14:50
 */
@TableName(value = "t_question_property")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionProperty {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    private Integer questionId;
    private String answer;
    private String knowledgePoint;
    private String chapter;
    private Float errorRate;
    private String errorPoint;
    private Integer difficulty;


}
