package com.oo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 学生类
 * @author: mango
 * @date: 2023/5/26 14:50
 */
@TableName(value = "t_student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String account;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
