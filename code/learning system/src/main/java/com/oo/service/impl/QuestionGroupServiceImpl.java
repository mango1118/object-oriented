package com.oo.service.impl;

import com.oo.domain.FillInTheBlankQuestionsDTO;
import com.oo.domain.MultipleChoiceQuestionsDTO;
import com.oo.domain.QuestionGroupDTO;
import com.oo.domain.SubjectiveQuestionsDTO;
import com.oo.service.QuestionGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: Dao的使用方法请学习mybatis-plus
 * @author: bxt
 * @date: 2023/6/23 15:34
 */
@Service
public class QuestionGroupServiceImpl implements QuestionGroupService {


    public void setMultipleChoiceQuestions(QuestionGroupDTO questions, List<MultipleChoiceQuestionsDTO> multipleChoiceQuestions) {
        questions.getMultipleChoiceQuestionsDTO().addAll(multipleChoiceQuestions);
    }


    public  void setFillInTheBlankQuestions(QuestionGroupDTO questions, List<FillInTheBlankQuestionsDTO> fillInTheBlankQuestions) {
        questions.getFillInTheBlankQuestionsDTO().addAll(fillInTheBlankQuestions);
    }


    public  void setSubjectiveQuestions(QuestionGroupDTO questions, List<SubjectiveQuestionsDTO> subjectiveQuestions) {
        questions.getSubjectiveQuestionsDTO().addAll(subjectiveQuestions);
    }
}
