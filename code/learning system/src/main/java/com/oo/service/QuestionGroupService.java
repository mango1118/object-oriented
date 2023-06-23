package com.oo.service;


import com.oo.domain.FillInTheBlankQuestionsDTO;
import com.oo.domain.MultipleChoiceQuestionsDTO;
import com.oo.domain.QuestionGroupDTO;
import com.oo.domain.SubjectiveQuestionsDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

import java.util.List;
/**
 * @description:
 * @author: bxt
 * @date: 2023/5/22 20:06
 */
@Transactional
public interface QuestionGroupService {
    /**
     * 通过id返回paper (name,totalscore)
     * 即查询试卷名字，总分
     *
     * @param
     * @return paper
     */
    public static void setMultipleChoiceQuestions(QuestionGroupDTO questions, List<MultipleChoiceQuestionsDTO> multipleChoiceQuestions) {
        if (questions != null && multipleChoiceQuestions != null) {
            if (questions.getMultipleChoiceQuestionsDTO() == null) {
                questions.setMultipleChoiceQuestionsDTO(new ArrayList<>());
            }
            questions.getMultipleChoiceQuestionsDTO().addAll(multipleChoiceQuestions);
        }
    }

    /**
     * 通过id返回paper (name,totalscore)
     * 即查询试卷名字，总分
     *
     * @param
     * @return paper
     */
    public static void setFillInTheBlankQuestions(QuestionGroupDTO questions, List<FillInTheBlankQuestionsDTO> fillInTheBlankQuestions) {
        if (questions != null && fillInTheBlankQuestions != null) {
            if (questions.getFillInTheBlankQuestionsDTO() == null) {
                questions.setFillInTheBlankQuestionsDTO(new ArrayList<>());
            }
            questions.getFillInTheBlankQuestionsDTO().addAll(fillInTheBlankQuestions);
        }
    }

    /**
     * 通过id返回paper (name,totalscore)
     * 即查询试卷名字，总分
     *
     * @param
     * @return paper
     */
    public static void setSubjectiveQuestions(QuestionGroupDTO questions, List<SubjectiveQuestionsDTO> subjectiveQuestions) {
        if (questions != null && subjectiveQuestions != null) {
            if (questions.getSubjectiveQuestionsDTO() == null) {
                questions.setSubjectiveQuestionsDTO(new ArrayList<>());
            }
            questions.getSubjectiveQuestionsDTO().addAll(subjectiveQuestions);
        }
    }


}