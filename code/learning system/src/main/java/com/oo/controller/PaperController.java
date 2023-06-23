package com.oo.controller;

import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.ClassPaper;
import com.oo.domain.Paper;
import com.oo.domain.StuClass;
import com.oo.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:29
 */
@RestController
@RequestMapping("/papers")
public class PaperController {



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


    /**
     * @description: 将所有试卷对应每个班，并保存数据库中进行初始化
     */
    @PostMapping("/saveClassPaper")
    public Result saveClasses() {
        List<ClassPaper> classPaper = PaperService.getAndSaveExamPaper();
        Integer code = classPaper != null ? Code.GET_OK : Code.GET_ERR;
        String msg = classPaper != null ? "班级所有可发试卷保存成功!" : "班级所有可发试卷保存错误，请重试！";
        return new Result(code, classPaper, msg);
        // 将classes保存到数据库中的班级表中
    }

}
