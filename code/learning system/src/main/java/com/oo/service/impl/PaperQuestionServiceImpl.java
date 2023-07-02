package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oo.dao.PaperGraphDao;
import com.oo.dao.PaperQuestionDao;
import com.oo.dao.QuestionDao;
import com.oo.dao.StudentDao;
import com.oo.domain.*;
import com.oo.service.PaperQuestionService;
import com.oo.service.QuestionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:08
 */
@Service
public class PaperQuestionServiceImpl implements PaperQuestionService {

    @Autowired
    //dao的使用方法请学习mybatis-plus
    private PaperQuestionDao paperQuestionDao;
    @Autowired
    //dao的使用方法请学习mybatis-plus
    private QuestionDao questionDao;

    public List<Integer> getQuestionsByPaperId(Integer id){
        QueryWrapper<PaperQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_id", id).select("question_id");
        List<PaperQuestion> paperQuestions = paperQuestionDao.selectList(queryWrapper);
        List<Integer> questionIds = new ArrayList<>();
        for (PaperQuestion paperQuestion : paperQuestions) {
            questionIds.add(paperQuestion.getQuestionId());
        }
        return questionIds;
    }

    public QuestionGroupDTO getQuestions(Integer paperId){
        List<Integer> questionList = paperQuestionDao.getQuestionIdsByPaperId(paperId);

        List<MultipleChoiceQuestionsDTO> multipleChoiceQuestions = new ArrayList<>();
        List<FillInTheBlankQuestionsDTO> fillInTheBlankQuestions = new ArrayList<>();
        List<SubjectiveQuestionsDTO> subjectiveQuestions = new ArrayList<>();

        int multipleChoiceIndex = 1;
        int fillInTheBlankIndex = 1;
        int subjectiveIndex = 1;

        for (Integer questionId : questionList) {
            Question question = questionDao.selectById(questionId);

            if (Objects.equals(question.getType(), "选择题")) {
                MultipleChoiceQuestionsDTO multipleChoiceQuestion = new MultipleChoiceQuestionsDTO();
                multipleChoiceQuestion.setId(multipleChoiceIndex);
                System.out.print("multipleChoiceIndex:"+multipleChoiceIndex);
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
        return  questions;
    }
}
