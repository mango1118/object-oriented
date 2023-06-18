package com.oo.domain.vo;


import com.oo.domain.Paper;
import com.oo.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *@title 题库和要创建的试卷
 *@author Administrator
 *@description
 *@create 2023/6/18 0018 4:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsPaper {
    private List<Question> questions;
    private Paper examPaper;
}
