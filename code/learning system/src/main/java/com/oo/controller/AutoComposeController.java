package com.oo.controller;

import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.Paper;
import com.oo.service.AutoComposeService;
import com.oo.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/autoCompose")
public class AutoComposeController {
    @Autowired
    //通过spring自动注入
    private AutoComposeService autoComposeService;

    @Autowired
    private PaperService paperService;

    @PostMapping("/submitForm")
    public Result getByFormData(@RequestBody ExamData examData){

        Integer total = examData.getTotalScore();
        Integer choice = examData.getMultipleChoiceCount();
        Integer fillIn = examData.getFillInTheBlankCount();
        Integer subjective = examData.getSubjectiveCount();
        Integer level = examData.getDifficulty();
        String name = examData.getName();
        Integer id = autoComposeService.autoCompose(name, total, choice, fillIn, subjective, level);

        Paper paper = paperService.selectById(id);
        Map<String, Object> paperQ = paperService.selectByPaperId(1, (choice + fillIn +subjective), id);

        Integer code = paper != null && paperQ != null ? Code.GET_OK : Code.GET_ERR;
        String msg = paper != null && paperQ != null ? "" : "数据查询失败，请重试！";

        return new Result(code, paper, paperQ, msg);
        //return new Result();
    }
}

class ExamData {
    private String name;
    private Integer totalScore;
    private Integer multipleChoiceCount;
    private Integer fillInTheBlankCount;
    private Integer subjectiveCount;
    private Integer difficulty;

    public String getName(){
        return  name;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public Integer getMultipleChoiceCount() {
        return multipleChoiceCount;
    }

    public Integer getFillInTheBlankCount() {
        return fillInTheBlankCount;
    }

    public Integer getSubjectiveCount() {
        return subjectiveCount;
    }

    public Integer getDifficulty() {
        return difficulty;
    }
}