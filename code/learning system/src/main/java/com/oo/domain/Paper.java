package com.oo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer Id;
    @TableField(value = "total_score")
    private Integer totalScore;
    private String name;
    private Integer enabled;

}
