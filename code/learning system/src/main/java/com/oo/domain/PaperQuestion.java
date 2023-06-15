package com.oo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 试卷-题目类
 * @author: mango
 * @date: 2023/5/26 14:50
 */
@TableName(value = "t_paper_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperQuestion {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer paperId;
    private Integer questionId;
    private Integer setScore;
}
