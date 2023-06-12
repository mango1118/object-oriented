package com.oo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.oo.domain.vo.Qcontent_score;
import com.oo.domain.Paper;
import com.oo.service.PaperService;
import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:29
 */
@RestController
@RequestMapping("/papers")
public class PaperController {

    @Autowired
    //通过spring自动注入
    private PaperService PaperService;

    @GetMapping("/{id}")
    //前端将在url中添加一个Integer类型的id，例如:localhost:9090/papers/1，注意使用get注解
    public Result getById(@PathVariable Integer id) {
        Paper paper = PaperService.selectById(id);
        Integer code = paper != null ? Code.GET_OK : Code.GET_ERR;
        String msg = paper != null ? "" : "数据查询失败，请重试！";

        return new Result(code, paper, msg);
    }
    @GetMapping("/content{id}")
    public Result getByPaperId(@PathVariable Integer id) {

        List<Qcontent_score>  paperQ = PaperService.selectByPaperId(id);
        Integer code = paperQ != null ? Code.GET_OK : Code.GET_ERR;
        String msg = paperQ != null ? "" : "数据查询失败，请重试！";
        return new Result(code, paperQ, msg);
    }
}
