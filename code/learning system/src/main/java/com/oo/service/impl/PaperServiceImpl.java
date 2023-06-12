package com.oo.service.impl;
import com.oo.dao.PaperDao;
import com.oo.dao.PaperQuestionDao;
import com.oo.dao.QuestionDao;
import com.oo.service.PaperService;
import com.oo.domain.Paper;
import com.oo.domain.PaperQuestion;
import com.oo.domain.Question;
import com.oo.domain.vo.Qcontent_score;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:08
 */
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperDao paperDao;
    @Autowired
    private PaperQuestionDao paperquestionDao;
    @Autowired
    private QuestionDao questionDao;

    @Override
    public Paper selectById(Integer id) {
        //System.out.println(paperDao.selectById(id));
        return paperDao.selectById(id);
    }

    @Override
    public List<Qcontent_score> selectByPaperId(Integer id){
        //查找某张试卷里的题目id和每道题目对应的score
        QueryWrapper<PaperQuestion> queryWrapper = new QueryWrapper<>();
        QueryWrapper<PaperQuestion> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.select("question_id").eq("paper_id",id);
        queryWrapper1.select("question_id", "set_score").eq("paper_id",id);
        List<Map<String, Object>> map = paperquestionDao.selectMaps(queryWrapper);
        List<Map<String, Object>> questionIdScores = paperquestionDao.selectMaps(queryWrapper1);
        List<Object> questionIds =  map.stream()
                .map(m -> m.get("question_id"))
                .collect(Collectors.toList());

        // 根据question_id列表查询question表content
        QueryWrapper<Question> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.select("id", "content").in("id", questionIds);
        List<Map<String, Object>> questionContents = questionDao.selectMaps(queryWrapper2);

        // 链接两个列表，封装到Qcontent_score中
        List<Qcontent_score> result = new ArrayList<>();
        for (Map<String, Object> qIdScore : questionIdScores) {
            Integer questionId = (Integer)qIdScore.get("question_id");
            Integer score = (Integer)qIdScore.get("set_score");

            for (Map<String, Object> question : questionContents) {

                if (question.get("id").equals(questionId)) {
                    Qcontent_score qcScore = new Qcontent_score();
                    qcScore.setSetScore(score);
                    qcScore.setContent((String)question.get("content"));
                    result.add(qcScore);
                    break;
                }
            }
        }
        return result;

    }
}
