package com.oo.controller;

import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.SearchScoreDTO;
import com.oo.domain.SelectExamsDTO;
import com.oo.service.StudentPaperService;
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
@RequestMapping("/studentPapers")
public class StudentPaperController {

    @Autowired
    private StudentPaperService studentPaperService;

    @GetMapping("/pageLike")
    public Result getStudentPapers(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "5") Integer pageSize,
                                   @RequestParam(required = false) String paperId,
                                   @RequestParam(required = false) String paperName,
                                   @RequestParam(required = false) String studentId) {
        System.out.println(paperId);
        System.out.println(paperName);
        System.out.println(studentId);

        Integer paperid = null;
        if (!"null".equals(paperId)) {
            paperid = Integer.valueOf(paperId);
        }

        Integer studentid = Integer.valueOf(studentId);

        List<SelectExamsDTO> selectExamsList = studentPaperService.selectLike(pageNum, pageSize, paperid, paperName,studentid);

        Integer code = selectExamsList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = selectExamsList != null ? "" : "数据查询失败，请重试！";
        return new Result(code, selectExamsList, msg);
    }

    @GetMapping("/pageLikeScore")
    public Result getStudentScore(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "5") Integer pageSize,
                                  @RequestParam(value = "studentPaperId", required = false) String paperId,
                                  @RequestParam(value = "studentPaperName", required = false) String paperName,
                                  @RequestParam(value = "studentId", required = false) String studentId) {
        System.out.println(paperId);
        System.out.println(paperName);
        System.out.println(studentId);

        Integer paperid = null;
        if (paperId != null) {
            paperid = Integer.valueOf(paperId);
        }

        Integer studentid = Integer.valueOf(studentId);

        List<SearchScoreDTO> selectExamsList = studentPaperService.selectScore(pageNum, pageSize, paperid, paperName,studentid);
        Integer code = selectExamsList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = selectExamsList != null ? "" : "数据查询失败，请重试！";
        return new Result(code, selectExamsList, msg);
    }

    @PostMapping("/submitForm")
    public String submitForm(@RequestBody Map<String, Object> formData) {
        // 在这里处理表单提交的逻辑
        // formData中包含了前端传递过来的选择题答案、填空题答案和主观题答案

        // 在这里可以进行进一步的数据处理、验证等操作
        // ...

        // 返回处理结果
        return "表单提交成功";
    }


}
