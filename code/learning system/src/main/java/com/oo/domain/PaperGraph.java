package com.oo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 试卷答案-图片类
 * @author: mango
 * @date: 2023/5/28 22:38
 */
@TableName(value = "t_paper_graph")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperGraph {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer studentPaperId;
    private Integer questionId;
    private String url;
    private Integer score;
    private String size;
    private Integer isMark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
