package com.oo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.oo.domain.Paper;
import com.oo.service.StudentPaperService;
import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:30
 */
@RestController
@RequestMapping("/studentPapers")
public class StudentPaperController {
    @Autowired
    //通过spring自动注入
    private StudentPaperService studentPaperService;

    @GetMapping("/pageLike/{pid}")
    //前端将在url中添加一个Integer类型的id，例如:localhost:9090/papers/1，注意使用get注解
    public Result getById(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5") Integer pageSize, @PathVariable String pid) {
        Integer id = Integer.valueOf(pid);
        Paper paper = studentPaperService.selectById(id);
        Map<String, Object>  paperQ = studentPaperService.selectByPaperId(pageNum, pageSize, id);

        Integer code =  paper != null && paperQ != null ? Code.GET_OK : Code.GET_ERR;
        String msg = paper != null && paperQ != null ? "" : "数据查询失败，请重试！";

        return new Result(code, paper, paperQ,  msg);
    }
    /*
    public Result getById(@RequestParam Integer id) {
        Paper paper = studentPaperService.selectById(id);
        List<Qcontent_score>  paperQ = studentPaperService.selectByPaperId(id);

        Integer code = paper != null && paperQ != null ? Code.GET_OK : Code.GET_ERR;
        String msg = paper != null && paperQ != null ? "" : "数据查询失败，请重试！";

        return new Result(code, paper, paperQ, msg);
    }*/
}
