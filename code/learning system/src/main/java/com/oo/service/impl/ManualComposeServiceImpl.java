package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.dao.PaperDao;
import com.oo.dao.PaperQuestionDao;
import com.oo.dao.QuestionDao;
import com.oo.dao.StudentDao;
import com.oo.domain.*;
import com.oo.domain.vo.QuestionsPaper;
import com.oo.service.ManualComposeService;
import com.oo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;


@Service
public class ManualComposeServiceImpl implements ManualComposeService {

    @Autowired
    private PaperDao paperDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private PaperQuestionDao paperQuestionDao;

    @Override
    public QuestionsPaper createPaper(Paper paper) {
        //创建试卷成功
        if( paperDao.insert(paper) > 0 ){
            List<Question> questions = questionDao.selectList(null);
            QuestionsPaper questionsPaper = new QuestionsPaper();
            questionsPaper.setQuestions(questions);
            questionsPaper.setExamPaper(paper);
            return questionsPaper;
        }

        return null;

    }

    @Override
    public boolean addQuestions(Integer id, List<QuestionSelectedDTO> questionSelectedDTOS) {
        Optional<Paper> optionalPaper = Optional.ofNullable(paperDao.selectById(id));
        if (optionalPaper.isPresent()) {
            Paper paper = optionalPaper.get();
            // 判断试卷总分是否超过设定值
            Integer totalScore = questionSelectedDTOS.stream().mapToInt(QuestionSelectedDTO::getScore).sum();
            if (!totalScore.equals(paper.getTotalScore())) {
                throw new IllegalArgumentException("总分不一致");
            }

            // 添加试题到试卷中
            List<PaperQuestion> savedPaperQuestions = new ArrayList<>();
            for (QuestionSelectedDTO questionSelectedDTO : questionSelectedDTOS) {
                //Optional<Question> optionalQuestion = questionRepository.findById(paperQuestion.getQuestion().getId());
                Question questionSelected = questionDao.selectById(questionSelectedDTO.getId());
                if (Objects.nonNull(questionSelected)) {
                    PaperQuestion savedPaperQuestion = new PaperQuestion();
                    savedPaperQuestion.setPaperId(id);
                    savedPaperQuestion.setQuestionId(questionSelected.getId());
                    savedPaperQuestion.setSetScore(questionSelectedDTO.getScore());
                    System.out.println(savedPaperQuestion);
                    savedPaperQuestions.add(savedPaperQuestion);
                } else {
                    throw new EntityNotFoundException("Question not found");
                }
            }
            paperQuestionDao.addBatch(savedPaperQuestions);
        } else {
            throw new EntityNotFoundException("Paper not found");
        }
        return false;
    }

//    @Override
//    public boolean addQuestions(Long id, List<Question> paperQuestions) {
//        // 获取试卷信息
// Optional<Paper> optionalPaper = Optional.ofNullable(paperDao.selectById(id));
//        if (optionalPaper.isPresent()) {
//            Paper paper = optionalPaper.get();
//            // 判断试卷总分是否超过设定值
//            Integer totalScore = paperQuestions.stream().mapToInt(PaperQuestion::getScore).sum();
//            if (totalScore > paper.getTotalScore()) {
//                throw new IllegalArgumentException("Total score of paper exceeds the limit");
//            }
//            // 添加试题到试卷中
//            List<PaperQuestion> savedPaperQuestions = new ArrayList<>();
//            for (PaperQuestion paperQuestion : paperQuestions) {
//                Optional<Question> optionalQuestion = questionRepository.findById(paperQuestion.getQuestion().getId());
//                if (optionalQuestion.isPresent()) {
//                    PaperQuestion savedPaperQuestion = new PaperQuestion();
//                    savedPaperQuestion.setPaper(paper);
//                    savedPaperQuestion.setQuestion(optionalQuestion.get());
//                    savedPaperQuestion.setScore(paperQuestion.getScore());
//                    savedPaperQuestions.add(savedPaperQuestion);
//                } else {
//                    throw new EntityNotFoundException("Question not found");
//                }
//            }
//            paperQuestionRepository.saveAll(savedPaperQuestions);
//        } else {
//            throw new EntityNotFoundException("Paper not found");
//        }
//        return false;
//    }



}
