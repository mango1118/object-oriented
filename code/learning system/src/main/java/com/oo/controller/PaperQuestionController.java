package com.oo.controller;

import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.*;
import com.oo.service.PaperQuestionService;
import com.oo.service.QuestionGroupService;
import com.oo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:29
 */
@RestController
@RequestMapping("/paperQuestions")
public class PaperQuestionController {

    @Autowired
    private PaperQuestionService paperQuestionService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/{paperId}")
    public Result getQuestionsByPaperId(@RequestParam Integer paperId) {
        // 查询数据库或者调用 Service 获取指定试卷的试题列表

        QuestionGroupDTO questions=paperQuestionService.getQuestions(paperId);

        //System.out.print("zheli:"+questions);
        Integer code = questions!= null  ? Code.GET_OK : Code.GET_ERR;
        String msg = questions != null ? "" : "数据查询失败，请重试！";
        return new Result(code,questions,msg);
    }



}
