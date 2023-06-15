package com.oo.controller;

import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.Paper;
import com.oo.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @GetMapping("/pageLike")
    //前端将在url中添加一个Integer类型的id，例如:localhost:9090/papers/1，注意使用get注解
    public Result getById(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam String pid) {
        Integer id = Integer.valueOf(pid);
        Paper paper = PaperService.selectById(id);
        Map<String, Object> paperQ = PaperService.selectByPaperId(pageNum, pageSize, id);

        Integer code = paper != null && paperQ != null ? Code.GET_OK : Code.GET_ERR;
        String msg = paper != null && paperQ != null ? "" : "数据查询失败，请重试！";

        return new Result(code, paper, paperQ, msg);
    }
}
