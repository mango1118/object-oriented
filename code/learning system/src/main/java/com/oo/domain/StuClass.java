package com.oo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "t_class")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuClass {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
//    @TableId(type = IdType.AUTO)
    private Integer id;
    private String className;
}
