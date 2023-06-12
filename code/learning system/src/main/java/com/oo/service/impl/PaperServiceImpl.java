package com.oo.service.impl;

import com.oo.service.PaperService;
import org.springframework.stereotype.Service;

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
    private ParperQuestionDao paperquestionDao;
    @Autowired
    private QuestionDao questionDao;

    @Override
    public Paper selectById(Integer id) {
        return paperDao.selectById(id);
    }

    @Override
    public List<Qcontent_score> selectByPaperId(Integer id){
        //查找某张试卷里的题目id和每道题目对应的score
        QueryWrapper<PaperQuestion> queryWrapper = new QueryWrapper<>();
        QueryWrapper<PaperQuestion> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.select("question_id").eq("paper_id",id);
        queryWrapper1.select("question_id", "set_score").eq("paper_id",id);
        List<Map<String, Object>> questionIds = paperquestionDao.selectList(queryWrapper);
        List<Map<String, Object>> questionIdScores = paperquestionDao.selectList(queryWrapper1);

        // 根据question_id列表查询question表content
        QueryWrapper<Question> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.select("id", "content").in("id", questionIds);
        List<Map<String, Object> questionContents = questionDao.selectList(queryWrapper2);

        // 链接两个列表，封装到Qcontent_score中
        List<Qcontent_score> result = new ArrayList<>();
        for (Map<String, Object> qIdScore : questionIdScores) {
            Integer questionId = (Integer)qIdScore.get("question_id");
            Integer score = (Integer)qIdScore.get("set_score");

            for (Map<String, Object> question : questionContents) {
                if (question.get("id").equals(questionId)) {
                    Qcontent_score qcScore = new Qcontent_score();
                    qcScore.setScore(score);
                    qcScore.setContent((String)question.get("content"));
                    result.add(qcScore);
                    break;
                }
            }
        }

        return result;

    }
}
