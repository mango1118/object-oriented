package com.oo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *@title
 *@author bxt
 *@description
 *@create 2023/6/21 17:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionGroupDTO {
    @JsonProperty("multipleChoiceQuestions")
    private List<MultipleChoiceQuestionsDTO> multipleChoiceQuestionsDTO;
    @JsonProperty("fillInTheBlankQuestions")
    private List<FillInTheBlankQuestionsDTO> fillInTheBlankQuestionsDTO;
    @JsonProperty("subjectiveQuestions")
    private List<SubjectiveQuestionsDTO> subjectiveQuestionsDTO;
}
