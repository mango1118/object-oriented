package com.oo.controller;

import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.Question;
import com.oo.domain.QuestionProperty;
import com.oo.domain.QuestionProperty;
import com.oo.service.QuestionPropertyService;
import com.oo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:30
 */
@RestController
@RequestMapping("/questionProperties")
public class QuestionPropertyController {
    @Autowired
    //通过spring自动注入
    private QuestionPropertyService questionPropertyService;

    /**
     * 查询某个问题的性质：1
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        QuestionProperty questionProperty = questionPropertyService.selectById(id);
        Integer code = questionProperty != null ? Code.GET_OK : Code.GET_ERR;
        String msg = questionProperty != null ? "" : "数据查询失败，请重试！";
        return new Result(code, questionProperty, msg);
    }

    /**
     * 修改问题性质
     * @param questionProperty
     * @return
     */
    @PutMapping
    public Result update(@RequestBody QuestionProperty questionProperty) {
        boolean flag = questionPropertyService.update(questionProperty);
        return new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag);
    }


    /**
     * 设置问题性质
     * @param questionProperty
     * @return
     */
    @PostMapping
    public Result save(@RequestBody QuestionProperty questionProperty) {
        boolean flag = questionPropertyService.save(questionProperty);
        return new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag);
    }


}
