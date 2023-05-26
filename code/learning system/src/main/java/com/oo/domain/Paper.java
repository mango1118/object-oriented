package com.oo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 试卷类
 * @author: mango
 * @date: 2023/5/26 14:49
 */
@TableName(value = "t_paper")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper {

    private Integer paperId;
    private Integer paperTotalScore;
    private Integer paperName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
