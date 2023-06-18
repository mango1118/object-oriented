package com.oo.controller;

import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.dao.QuestionDao;
import com.oo.domain.Paper;
import com.oo.domain.Question;
import com.oo.domain.QuestionSelectedDTO;
import com.oo.domain.Student;
import com.oo.domain.vo.QuestionsPaper;
import com.oo.service.ManualComposeService;
import com.oo.service.PaperService;
import com.oo.service.QuestionService;
import com.oo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description: 手动组卷控制类
 * @author: mango
 * @date: 2023/5/26 15:30
 */
@RestController
@RequestMapping("/manualCompose")
public class ManualComposeController {

    @Autowired
    private ManualComposeService manualComposeService;
    @Autowired
    private QuestionService questionService;


    //

    /**
     * 创建试卷：名称+总分
     * @param paper
     * @return
     */
    @PostMapping("/create")
    public Result createPaper(@RequestBody Paper paper){
        //System.out.println("试卷："+paper);
        if (paper.getName() == null || paper.getTotalScore() == null || paper.getTotalScore() <= 0) {
            throw new IllegalArgumentException("试卷名称和总分不能为空或为负");
        }
        QuestionsPaper questionsP = manualComposeService.createPaper(paper);
        Integer code = questionsP != null ? Code.GET_OK : Code.GET_ERR;
        String msg = questionsP != null ? "" : "数据查询失败，请重试！";
        return new Result(code, questionsP, msg);
    }

    /**
     * 选择多条题目作为试卷保存数据库（未完成）
     * @param paperId
     * @param questionSelectedDTOS
     * @return
     */
    @PostMapping("/save")
    public Result addQuestions(@RequestParam Integer paperId, @RequestBody List<QuestionSelectedDTO> questionSelectedDTOS) {
        System.out.println(paperId);
        System.out.println(questionSelectedDTOS);
        // 参数校验
        if (questionSelectedDTOS == null || questionSelectedDTOS.isEmpty()) {
            throw new IllegalArgumentException("试卷题目不能为空或为null");
        }
        // 调用业务逻辑层添加试题
        boolean flag = manualComposeService.addQuestions(paperId, questionSelectedDTOS);
        return new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag);
    }

    /*@GetMapping("/{id}")
    public Paper getPaper(@PathVariable Long id) {
        // 调用业务逻辑层获取试卷信息
        return paperService.getPaper(id);
    }*/



}