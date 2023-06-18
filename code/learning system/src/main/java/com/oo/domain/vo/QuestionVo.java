package com.oo.domain.vo;


import com.oo.domain.Question;
import com.oo.domain.QuestionProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@title
 *@author mqx
 *@description
 *@create 2023/6/18 0018 23:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVo {
    private Integer questionId;
    private String content;
    private String type;
    private String answer;
    private String knowledgePoint;
    private String chapter;
    private Float errorRate;
    private String errorPoint;
    private Integer difficulty;

}
