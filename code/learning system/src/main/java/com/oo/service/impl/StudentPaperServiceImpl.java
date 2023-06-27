package com.oo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oo.dao.*;
import com.oo.domain.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oo.service.StudentPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:09
 */
@Service
public class StudentPaperServiceImpl implements StudentPaperService {

    @Autowired
    //dao的使用方法请学习mybatis-plus
    private StudentPaperDao studentPaperDao;
    @Autowired
    //dao的使用方法请学习mybatis-plus
    private PaperDao paperDao;
    @Autowired
    //dao的使用方法请学习mybatis-plus
    private StudentDao studentDao;
    @Autowired
    //dao的使用方法请学习mybatis-plus
    private PaperQuestionDao paperQuestionDao;
    @Autowired
    //dao的使用方法请学习mybatis-plus
    private QuestionDao questionDao;
    @Autowired
    //dao的使用方法请学习mybatis-plus
    private QuestionPropertyDao questionPropertyDao;
    @Autowired
    //dao的使用方法请学习mybatis-plus
    private PaperGraphDao paperGraphDao;


    @Override
    public List<SelectExamsDTO> selectLike(Integer pageNum, Integer pageSize, Integer paperId, String paperName, Integer id) {
        LambdaQueryWrapper<StudentPaper> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentPaper::getStudentId, id);


        // 如果传入的paperId不为空，设置查询条件：paper_id等于传入的paperId
        if (paperId != null) {
            wrapper.eq(StudentPaper::getPaperId, paperId);
        }

        // 如果传入的paperName不为空，进行子查询，根据paperName查询对应的paperId
        if (paperName != null) {
            // 创建LambdaQueryWrapper对象，用于构建paper表的查询条件
            LambdaQueryWrapper<Paper> paperWrapper = new LambdaQueryWrapper<>();
            paperWrapper.eq(Paper::getName, paperName);

            // 查询paper表，获取paperId
            Paper paper = paperDao.selectOne(paperWrapper);

            System.out.println("Paper: " + paper); // 检查paper对象是否为空，以及获取到的值是否正确
            // 如果paperId存在，则设置查询条件：paper_id等于paperId
            if (paper != null) {
                wrapper.eq(StudentPaper::getPaperId, paper.getId());
            }
        }

        Page<StudentPaper> page = new Page<>(pageNum, pageSize);
        IPage<StudentPaper> resultPage = studentPaperDao.selectPage(page, wrapper);

        List<StudentPaper> studentPapers = resultPage.getRecords();
        System.out.println(studentPapers);
        List<SelectExamsDTO> selectExamsDTOs = new ArrayList<>();
        for (StudentPaper studentPaper : studentPapers) {
            SelectExamsDTO selectExamsDTO = new SelectExamsDTO();
            // 设置需要的DTO属性值
            selectExamsDTO.setPaperId(studentPaper.getPaperId());

            // 根据paperId从paper表中查询paperName
            LambdaQueryWrapper<Paper> paperWrapper = new LambdaQueryWrapper<>();
            paperWrapper.eq(Paper::getId, studentPaper.getPaperId());
            Paper paper = paperDao.selectOne(paperWrapper);
            if (paper != null) {
                selectExamsDTO.setPaperName(paper.getName());
            }

            selectExamsDTOs.add(selectExamsDTO);
        }
        return selectExamsDTOs;
    }

    @Override
    public List<SearchScoreDTO> selectScore(Integer pageNum, Integer pageSize, Integer examId, String paperName, Integer studentId) {
        LambdaQueryWrapper<StudentPaper> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentPaper::getStudentId, studentId);

        // 如果传入的examId不为空，设置查询条件：paper_id 等于传入的 examId
        if (examId != null) {
            wrapper.eq(StudentPaper::getPaperId, examId);
        }

        // 如果传入的paperName不为空，进行子查询，根据paperName查询对应的examId
        if (paperName != null) {
            // 创建LambdaQueryWrapper对象，用于构建 Paper 表的查询条件
            LambdaQueryWrapper<Paper> paperWrapper = new LambdaQueryWrapper<>();
            paperWrapper.eq(Paper::getName, paperName);

            // 查询 Paper 表，获取 examId
            Paper paper = paperDao.selectOne(paperWrapper);

            System.out.println("Paper: " + paper); // 检查 paper 对象是否为空，以及获取到的值是否正确
            // 如果 examId 存在，则设置查询条件：paper_id 等于 examId
            if (paper != null) {
                wrapper.eq(StudentPaper::getPaperId, paper.getId());
            }
        }

        Page<StudentPaper> page = new Page<>(pageNum, pageSize);
        IPage<StudentPaper> resultPage = studentPaperDao.selectPage(page, wrapper);

        List<StudentPaper> studentPapers = resultPage.getRecords();
        System.out.println(studentPapers);
        List<SearchScoreDTO> searchScoreDTOs = new ArrayList<>();
        for (StudentPaper studentPaper : studentPapers) {
            SearchScoreDTO searchScoreDTO = new SearchScoreDTO();
            // 设置需要的 DTO 属性值
            searchScoreDTO.setStudentId(studentPaper.getStudentId());

            // 根据 studentId 从 Student 表中查询 studentName
            LambdaQueryWrapper<Student> studentWrapper = new LambdaQueryWrapper<>();
            studentWrapper.eq(Student::getId, studentPaper.getStudentId());
            Student student = studentDao.selectOne(studentWrapper);
            if (student != null) {
                searchScoreDTO.setStudentName(student.getName());
            }

            // 根据 examId（即 paperId）从 Paper 表中查询 paperName
            LambdaQueryWrapper<Paper> paperWrapper = new LambdaQueryWrapper<>();
            paperWrapper.eq(Paper::getId, studentPaper.getPaperId());
            Paper paper = paperDao.selectOne(paperWrapper);
            if (paper != null) {
                searchScoreDTO.setPaperName(paper.getName());
            }

            // 根据 examId（即 paperId）从 studentPaper 表中查询 score
            /*LambdaQueryWrapper<StudentPaper> studentPaperWrapper = new LambdaQueryWrapper<>();
            studentPaperWrapper.eq(StudentPaper::getPaperId, examId);

            StudentPaper studentPaper_score = studentPaperDao.selectOne(studentPaperWrapper);
            if (studentPaper_score != null) {
                searchScoreDTO.setScore(studentPaper_score.getTotal_score());
            }*/
            searchScoreDTO.setScore(studentPaper.getTotalScore());

            searchScoreDTO.setExamId(studentPaper.getPaperId());

            searchScoreDTOs.add(searchScoreDTO);
        }
        return searchScoreDTOs;
    }

    @Override
    public Integer getobjectivescore(Integer paperid, Integer studentid, List<String> multipleChoiceAnswers, List<String> fillInTheBlankAnswers) {
        // 获取paper_question表中对应paperid的questionid
        List<Integer> questionIds = paperQuestionDao.getQuestionIdsByPaperId(paperid);

        Integer objectiveScore = 0; // 记录客观题分数

        Integer index1=0;
        Integer index2=0;

        // 遍历questionIds
        for (int i = 0; i < questionIds.size(); i++) {
            Integer questionId = questionIds.get(i);

            // 根据questionId在question表中读取题目类型
            String questionType = questionDao.getQuestionTypeById(questionId);

            // 根据questionId在question_property表中读取答案
            String answer = questionPropertyDao.getAnswerById(questionId);

            // 获取题目分值
            Integer score = paperQuestionDao.getScoreByPaperIdAndQuestionId(paperid, questionId);

            // 判断题目类型是选择题还是填空题
            if (questionType.equals("选择题")) {
                // 如果是选择题，判断多选题答案是否正确
                String multipleChoiceAnswer = multipleChoiceAnswers.get(index1);
                String option = multipleChoiceAnswer.substring(0, 1); // 截取第一个字符
                System.out.println("选择题选项：" + option);

                index1++;
                System.out.println("是否正确：" + option.equals(answer));
                if (option.equals(answer)) {
                    objectiveScore += score; // 答案正确，加上题目分值
                }
            } else if (questionType.equals("填空题")) {
                // 如果是填空题，判断填空题答案是否正确
                String fillInTheBlankAnswer = fillInTheBlankAnswers.get(index2);
                System.out.println("填空题学生答案："+fillInTheBlankAnswer);
                index2++;
                System.out.println("是否正确：" + fillInTheBlankAnswer.equals(answer));
                if (fillInTheBlankAnswer.equals(answer)) {
                    System.out.println("填空题加分前：" + objectiveScore);
                    objectiveScore += score; // 答案正确，加上题目分值
                    System.out.println("填空题加分后：" + objectiveScore);
                }
            }

            // 将答案数组移到下一个答案（如果需要的话）
            // moveAnswerArray();
        }

        studentPaperDao.updateTotalScore(paperid, studentid, objectiveScore);
        return objectiveScore;
    }


    @Override
    public void savesubjectiveAnswers(Integer paperid, Integer studentid, List<String> subjectiveAnswers) {
        // 获取学生试卷ID
        Integer studentPaperId = studentPaperDao.getStudentPaperIdByPaperIdAndStudentId(paperid, studentid);

        // 获取试卷所有题目的ID列表
        List<Integer> questionIds = paperQuestionDao.getQuestionIdsByPaperId(paperid);

        Integer index=0;
        // 遍历题目ID列表
        for (int i = 0; i < questionIds.size(); i++) {
            Integer questionId = questionIds.get(i);

            // 获取题目类型
            String questionType = questionDao.getQuestionTypeById(questionId);

            // 判断题目类型是否为主观题
            if (questionType.equals("主观题")) {
                // 从subjectiveAnswers列表中获取主观题答案
                String subjectiveAnswer = subjectiveAnswers.get(index);
                index++;

                // 创建PaperGraph对象
                PaperGraph paperGraph = new PaperGraph();
                paperGraph.setStudentPaperId(studentPaperId);
                paperGraph.setQuestionId(questionId);
                paperGraph.setUrl(subjectiveAnswer);

                // 将PaperGraph对象插入到表格中
                paperGraphDao.insert(paperGraph);
            }
        }
    }

}
