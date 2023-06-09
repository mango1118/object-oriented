package com.oo.controller;

import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.PaperGraph;
import com.oo.domain.StuClass;
import com.oo.service.PaperService;
import com.oo.service.StudentService;
import com.oo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:30
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    private StudentService studentService;
    private PaperService paperService;


    /**
     * @description: 获取所有班级
     * @param
     * @return
     */
    @GetMapping("/classes")
    public Result getAllClasses() {
        List <StuClass> firstClasses = teacherService.getClasses();
        List<Map<String, Object>> classes = new ArrayList<>();
        for (StuClass stuClass : firstClasses) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", stuClass.getId());
            map.put("name", stuClass.getClassName());
            classes.add(map);
        }
        Integer code = !classes.isEmpty() ? Code.GET_OK : Code.GET_ERR;
        String msg = !classes.isEmpty() ? "所有班级查找成功" : "数据查询失败，请重试！";
        return new Result(code, classes, msg);
    }


    /**
     * @description: 选择班级后显示该班级所有可用于组卷的信息
     * @param
     * @return
     */
    @GetMapping("/papers")
    public Result getTheClassPaper(@RequestParam("classId") Integer classId){
        List <Map<String,Object>> papers = teacherService.getTheClassPapers(classId).stream()
                .map(map -> {
                    Map<String, Object> allPaper = new LinkedHashMap<>();
                    allPaper.put("id", map.get("paper_id"));
                    allPaper.put("name", map.get("paper_name"));
                    allPaper.put("sent", map.get("sent"));
                    return allPaper;
                })
                .collect(Collectors.toList());

        Integer code = !papers.isEmpty() ? Code.GET_OK : Code.GET_ERR;
        String msg = !papers.isEmpty() ? "该班级所有可用试卷查找成功" : "数据查询失败，请重试！";
        return new Result(code, papers, msg);
    }

    /**
     * @description: 选择一个班级发卷并将班级对应试卷id匹配到该班级中所有的学生
     * @param
     * @return
     */
    @PostMapping("/send-paper")
    public Result save(@RequestBody Map<String, Object> params){
        Integer paperId = (Integer) params.get("paperId");
        Integer classId = (Integer) params.get("classId");
        boolean flag = teacherService.chooseClass(paperId, classId);
        Integer code = flag == true ? Code.GET_OK : Code.GET_ERR;
        String msg;
        if(flag)
            msg = "试卷发送成功！";
       else
            msg = "试卷发送失败，请重试！";
        return new Result(code, msg);
    }


    /**
     * @description: 获取要改卷的内容
     * @param
     * @return
     */
    @GetMapping("/questions")
    public Result getTheClassPaper()
    {
        Map<String,Object> paperGraphs = teacherService.getObjContent();
        Integer code = !paperGraphs.isEmpty() ? Code.GET_OK : Code.GET_ERR;
        String msg = !paperGraphs.isEmpty() ? "教师改卷视图返回成功" : "教师改卷视图返回失败，请重试！";
        return new Result(code, paperGraphs, msg);
    }

    /**
     * @description: 教师改卷给分并保存
     * @param
     * @return
     */
    @PostMapping("/submit-score")
    public Result giveScore(@RequestBody Map<String, Object> data)
    {
        Integer Id = (Integer) data.get("questionId");
        Integer teacherScore = (Integer) data.get("teacherScore");
        boolean flag = teacherService.giveScore(Id, teacherScore);
        Integer code = flag ? Code.GET_OK : Code.GET_ERR;
        String msg;
        if(flag)
            msg = "主观题成绩保存成功！";
        else
            msg = "主观题成绩保存失败，请重试！";
        return new Result(code, msg);
    }
}
