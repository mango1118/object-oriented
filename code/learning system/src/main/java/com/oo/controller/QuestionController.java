package com.oo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.Question;
import com.oo.domain.Student;
import com.oo.domain.vo.QuestionVo;
import com.oo.service.QuestionPropertyService;
import com.oo.service.QuestionService;
import com.oo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:30
 */
@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;


//    /**
//     * @description: 根据id查询题目
//     */
//    @GetMapping("/{id}")
//    public Result getById(@PathVariable int id) {
//        Question question = questionService.getQuestionById(id);
//        Integer code = question != null ? Code.GET_OK : Code.GET_ERR;
//        String msg = question != null ? "" : "数据查询失败，请重试！";
//        return new Result(code, question, msg);
//    }


    /**
     * @description: 增加题目
     */
    @PostMapping()
    public Result save(@RequestBody Question question){
        boolean flag = questionService.addQuestion(question);
        Integer code = flag == true ? Code.GET_OK : Code.GET_ERR;
        String msg = question != null ? "题目添加成功！" : "题目添加失败，请重试！";
        return new Result(code, msg);
    }


    /**
     * @description: 根据id删除题目
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = questionService.deleteQuestion(id);
        Integer code = flag == true ? Code.GET_OK : Code.GET_ERR;
        String msg = flag==true ? "删除成功" : "删除失败，请重试！";
        return new Result(code, msg);
    }

//    /**
//     * @description: 修改题目
//     */
//    @PutMapping
//    public Result update(@RequestBody Question question) {
//        boolean flag = questionService.modifyQuestion(question);
//        return new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag);
//    }


}

