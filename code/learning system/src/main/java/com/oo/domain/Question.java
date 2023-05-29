package com.oo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 题目类
 * @author: mango
 * @date: 2023/5/26 14:50
 */
@TableName(value = "t_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String content;
    private Integer type;
    private String correctAnswer;
    private String knowledgePoint;
    private String chapter;
    private Float errorRate;
    private String errorPoint;
    private Integer difficulty;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
