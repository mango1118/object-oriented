package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.dao.PaperDao;
import com.oo.dao.PaperQuestionDao;
import com.oo.dao.QuestionDao;
import com.oo.domain.Paper;
import com.oo.domain.PaperQuestion;
import com.oo.domain.Question;
import com.oo.domain.vo.Qcontent_score;
import com.oo.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    public Integer selectcount(Integer id){
        QueryWrapper<PaperQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("question_id").eq("paper_id",id);

        return paperquestionDao.selectCount(queryWrapper);
    }
    @Override
    public Map<String, Object> selectByPaperId(Integer currentPage, Integer pageSize, Integer id){
        //查找某张试卷里的题目id和每道题目对应的score
        Integer max = selectcount(id);
        Integer maxPage = max % pageSize == 0? (max / pageSize) : (max / pageSize + 1);
        currentPage = currentPage > maxPage ? maxPage : currentPage;
        Page<PaperQuestion> page1 = new Page<>(currentPage, pageSize);
        QueryWrapper<PaperQuestion> queryWrapper = new QueryWrapper<>();
        QueryWrapper<PaperQuestion> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.select("question_id").eq("paper_id",id);
        queryWrapper1.select("question_id", "set_score").eq("paper_id",id);
        List<PaperQuestion> score = paperquestionDao.selectPage(page1, queryWrapper1).getRecords();
        Integer total = (int)paperquestionDao.selectPage(page1, queryWrapper).getTotal();
        List<Map<String, Object>> map = paperquestionDao.selectMaps(queryWrapper);

        List<Object> questionIds =  map.stream()
                .map(m -> m.get("question_id"))
                .collect(Collectors.toList());

        // 根据question_id列表查询question表content
        Page<Question> page2 = new Page<>(currentPage, pageSize);
        QueryWrapper<Question> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.select("id", "content").in("id", questionIds);
        List<Question> questionContents = questionDao.selectPage(page2, queryWrapper2).getRecords();

        // 链接两个列表，封装到Qcontent_score中
        List<Qcontent_score> result = new ArrayList<>();
        for (PaperQuestion qIdScore : score) {
            Integer questionId = qIdScore.getQuestionId();
            Integer score1 = qIdScore.getSetScore();

            for (Question question : questionContents) {

                if (question.getId().equals(questionId)) {
                    Qcontent_score qcScore = new Qcontent_score();
                    qcScore.setScore(score1);
                    qcScore.setContent(question.getContent());
                    result.add(qcScore);
                    break;
                }
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("data", result);
        resultMap.put("total", total);

        return resultMap;

    }


}
