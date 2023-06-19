package com.oo.domain.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("questionId")
    private Integer id;
    private String content;
    private String type;
    private String answer;
    private String knowledgePoint;
    private String chapter;
    private Float errorRate;
    private String errorPoint;
    private Integer difficulty;

}
