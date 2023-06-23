package com.oo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 供每个班发卷选择的试卷表
 */
@TableName(value = "t_class_paper")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassPaper {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
//    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer classId;
    private Integer paperId;
    private String paperName;
    private boolean sent;
}
