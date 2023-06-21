package com.oo.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@title 搜索题目的条件
 *@author mqx
 *@description
 *@create 2023/6/19 0019 20:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSearchDTO {
    private Integer questionId;
    private String type;
    private String knowledgePoint;
    private String chapter;
    private Integer offset;
    private Integer size;
}
