package com.oo.service;

import com.oo.domain.Paper;
import com.oo.domain.Question;
import com.oo.domain.Student;
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
     * 输入试卷名称和总分，保存设置
     * @param paper
     * @return
     */
    public boolean createPaper(Paper paper);


    /**
     *
     * @param id 试卷编号
     * @param paperQuestions 选择的题目集合
     * @return
     */
    boolean addQuestions(Long id, List<Question> paperQuestions);
}
