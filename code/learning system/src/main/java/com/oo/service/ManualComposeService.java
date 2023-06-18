package com.oo.service;

import com.oo.domain.Paper;
import com.oo.domain.Question;
import com.oo.domain.QuestionSelectedDTO;
import com.oo.domain.Student;
import com.oo.domain.vo.QuestionsPaper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description:学生服务类
 * @author: mango
 * @date: 2023/5/26 15:34
 */
@Transactional
public interface ManualComposeService {

    /**
     * 创建试卷，返回全部题目列表
     * @param paper
     * @return
     */
    public QuestionsPaper createPaper(Paper paper);


    /**
     *
     * @param id 试卷编号
     * @param questionSelectedDTOS 选择的题目集合
     * @return
     */
    boolean addQuestions(Integer id, List<QuestionSelectedDTO> questionSelectedDTOS);



}
