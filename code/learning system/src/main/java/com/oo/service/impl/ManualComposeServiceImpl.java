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
        paper.setEnabled(0);
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
    public boolean addQuestions(PaperDTO paperDTO) {
        Optional<Paper> optionalPaper = Optional.ofNullable(paperDao.selectById(paperDTO.getPaperId()));
        Paper paper = optionalPaper.get();
        if (optionalPaper.isPresent()) {

            // 判断试卷总分是否超过设定值
            Integer totalScore = paperDTO.getQuestionSelectedDTOS().stream().mapToInt(QuestionSelectedDTO::getScore).sum();
            if (!totalScore.equals(paper.getTotalScore())) {
                throw new IllegalArgumentException("总分不一致");
            }

            // 添加试题到试卷中
            List<PaperQuestion> savedPaperQuestions = new ArrayList<>();
            for (QuestionSelectedDTO questionSelectedDTO : paperDTO.getQuestionSelectedDTOS()) {
                //Optional<Question> optionalQuestion = questionRepository.findById(paperQuestion.getQuestion().getId());
                //找出每条题目
                Question questionSelected = questionDao.selectById(questionSelectedDTO.getId());
                if (Objects.nonNull(questionSelected)) {
                    PaperQuestion savedPaperQuestion = new PaperQuestion();
                    savedPaperQuestion.setPaperId(paperDTO.getPaperId());
                    savedPaperQuestion.setQuestionId(questionSelected.getId());
                    savedPaperQuestion.setSetScore(questionSelectedDTO.getScore());
                    System.out.println(savedPaperQuestion);
                    savedPaperQuestions.add(savedPaperQuestion);
                } else {
                    throw new EntityNotFoundException("Question not found");
                }
            }
            paperQuestionDao.addBatch(savedPaperQuestions);
            //修改试卷为启用
            paper.setEnabled(1);
            paperDao.updateById(paper);
        } else {
            throw new EntityNotFoundException("Paper not found");
        }
        return false;
    }

}
