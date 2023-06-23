package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.dao.*;
import com.oo.domain.*;
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

    @Autowired
    private StuClassDao stuClassDao;

    @Autowired
    private ClassPaperDao classPaperDao;

    @Override
    public Paper selectById(Integer id) {
        //System.out.println(paperDao.selectById(id));
        return paperDao.selectById(id);
    }

    @Override
    public Integer selectcount(Integer id) {
        QueryWrapper<PaperQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("question_id").eq("paper_id", id);

        return paperquestionDao.selectCount(queryWrapper);
    }

    @Override
    public Map<String, Object> selectByPaperId(Integer currentPage, Integer pageSize, Integer id) {
        //查找某张试卷里的题目id和每道题目对应的score
        Integer max = selectcount(id);
        Integer maxPage = max % pageSize == 0 ? (max / pageSize) : (max / pageSize + 1);
        currentPage = currentPage > maxPage ? maxPage : currentPage;
        Page<PaperQuestion> page1 = new Page<>(currentPage, pageSize);
        QueryWrapper<PaperQuestion> queryWrapper = new QueryWrapper<>();
        QueryWrapper<PaperQuestion> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.select("question_id").eq("paper_id", id);
        queryWrapper1.select("question_id", "set_score").eq("paper_id", id);
        List<PaperQuestion> score = paperquestionDao.selectPage(page1, queryWrapper1).getRecords();
        Integer total = (int) paperquestionDao.selectPage(page1, queryWrapper).getTotal();
        List<Map<String, Object>> map = paperquestionDao.selectMaps(queryWrapper);

        List<Object> questionIds = map.stream()
                .map(m -> m.get("question_id"))
                .collect(Collectors.toList());

        // 根据question_id列表查询question表content
        Page<Question> page2 = new Page<>(currentPage, pageSize);
        QueryWrapper<Question> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.select("id", "content", "type").in("id", questionIds);
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
                    qcScore.setType(question.getType());
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


    /**
     * @description: 获取全部试卷并将其存在class_paper中,同时将班级id也加进去,并初始化sent
     */
    @Override
    public List<ClassPaper> getAndSaveExamPaper() {
        List<Map<String,Object>> getPapers = paperDao.getAllExamPapers();
        System.out.println(getPapers);
        List<ClassPaper> classPapers = new ArrayList<>();
        List<Integer> getClassId = stuClassDao.getAllClassId();
        Integer j = 0;
        Integer id = 0;
        for (Integer clazz : getClassId) {
            for (Map<String, Object> examPapers : getPapers){
                if(j<=getClassId.indexOf(clazz))
                {
                    id++;
                }
                ClassPaper classPaper = new ClassPaper();
                classPaper.setId(id);
                classPaper.setClassId(clazz);
                classPaper.setPaperId((Integer) examPapers.get("id"));
                classPaper.setPaperName((String) examPapers.get("name"));
                classPaper.setSent(false);
                System.out.println(classPaper);
                if(classPaperDao.insert(classPaper)<0)
                {
                    classPapers = null;
                    return classPapers;
                }
                //classPapers.add(classPaper);
                //一个一个保存数据库中
            }
            j++;
        }

        classPapers = classPaperDao.selectList(null);
//        for (Integer clazz : getClassId) {
//            for (Map<String, Object> examPapers : getPapers){
//                ClassPaper classPaper = new ClassPaper();
//                classPaper.setClassId(clazz);
//                classPaper.setPaperId((Integer) examPapers.get("id"));
//                classPaper.setPaperName((String) examPapers.get("name"));
//                classPaper.setSent(false);
//                System.out.println(classPaper);
//                classPapers.add(classPaper);
//            }
//        }
        return classPapers;
    }

}
