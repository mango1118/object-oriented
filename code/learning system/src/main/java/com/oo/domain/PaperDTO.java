package com.oo.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *@title
 *@author Administrator
 *@description
 *@create 2023/6/18 0018 19:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDTO {
    private Integer paperId;
    @JsonProperty("selectedQuestions")
    private List<QuestionSelectedDTO> questionSelectedDTOS;
}
