package com.oo.controller;

import com.oo.domain.*;
import com.oo.service.PaperQuestionService;
import com.oo.service.QuestionGroupService;
import com.oo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:29
 */
@RestController
@RequestMapping("/paperQuestions")
public class PaperQuestionController {

    @Autowired
    private PaperQuestionService paperQuestionService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/{paperId}")
    public QuestionGroupDTO getQuestionsByPaperId(@RequestParam Integer paperId) {
        // 查询数据库或者调用 Service 获取指定试卷的试题列表
        List<Integer> questionList = paperQuestionService.getQuestionsByPaperId(paperId);

        List<MultipleChoiceQuestionsDTO> multipleChoiceQuestions = new ArrayList<>();
        List<FillInTheBlankQuestionsDTO> fillInTheBlankQuestions = new ArrayList<>();
        List<SubjectiveQuestionsDTO> subjectiveQuestions = new ArrayList<>();

        int multipleChoiceIndex = 1;
        int fillInTheBlankIndex = 1;
        int subjectiveIndex = 1;

        for (Integer questionId : questionList) {
            Question question = questionService.getQuestionById(questionId);

            if (Objects.equals(question.getType(), "选择题")) {
                MultipleChoiceQuestionsDTO multipleChoiceQuestion = new MultipleChoiceQuestionsDTO();
                multipleChoiceQuestion.setId(multipleChoiceIndex);
                //System.out.print("multipleChoiceIndex:"+multipleChoiceIndex);
                multipleChoiceQuestion.setQuestion(question.getContent());
                String content = question.getContent(); // 假设获取到的内容存储在变量 content 中

                Pattern pattern = Pattern.compile("[A-D]\\..*?(\\s|$)");
                Matcher matcher = pattern.matcher(content);

                List<String> options = new ArrayList<>();
                while (matcher.find()) {
                    String option = matcher.group().replaceAll("\\s", "");
                    options.add(option);
                }

                multipleChoiceQuestion.setOptions(options);

                // 其他属性设置...
                multipleChoiceQuestions.add(multipleChoiceQuestion);
                multipleChoiceIndex++;
            } else if (Objects.equals(question.getType(), "填空题")) {
                FillInTheBlankQuestionsDTO fillInTheBlankQuestion = new FillInTheBlankQuestionsDTO();
                fillInTheBlankQuestion.setId(fillInTheBlankIndex);
                //System.out.print("fillInTheBlankIndex:"+fillInTheBlankIndex);
                fillInTheBlankQuestion.setQuestion(question.getContent());
                // 其他属性设置...

                fillInTheBlankQuestions.add(fillInTheBlankQuestion);
                fillInTheBlankIndex++;
            } else if (Objects.equals(question.getType(), "主观题")) {
                SubjectiveQuestionsDTO subjectiveQuestion = new SubjectiveQuestionsDTO();
                subjectiveQuestion.setId(subjectiveIndex);
                //System.out.print("subjectiveIndex:"+subjectiveIndex);
                subjectiveQuestion.setQuestionImage(question.getContent());
                // 其他属性设置...
                subjectiveQuestions.add(subjectiveQuestion);
                subjectiveIndex++;
            }
        }
        QuestionGroupDTO questions = new QuestionGroupDTO();

        QuestionGroupService.setMultipleChoiceQuestions(questions, multipleChoiceQuestions);
        QuestionGroupService.setFillInTheBlankQuestions(questions, fillInTheBlankQuestions);
        QuestionGroupService.setSubjectiveQuestions(questions, subjectiveQuestions);
        //System.out.print("zheli:"+questions);

        return questions;
    }



}
