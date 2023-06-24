package com.oo.controller;

import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.StuClass;
import com.oo.domain.Student;
import com.oo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

/**
 * @description: 学生控制类
 * @author: mango
 * @date: 2023/5/26 15:30
 */
@RestController
// 访问路径:localhost:9090/students
@RequestMapping("/student")
public class StudentController {

    /**
     * 请学习使用rest风格进行注解
     * 返回的数据一律使用com.oo.controller.result类进行包装
     * 以下为实例
     */

    @Autowired
    //通过spring自动注入
    private StudentService studentService;

    @PostMapping("/saveStudents")
    //前端将在请求体中发送一个Student类型的实例，注意使用post注解
    public Result save(@RequestBody Student student) {
        boolean flag = studentService.save(student);
        return new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag);
    }

    @PutMapping
    //前端将在请求体中发送一个Student类型的实例，注意使用put注解
    public Result update(@RequestBody Student student) {
        boolean flag = studentService.update(student);
        return new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag);
    }

    @DeleteMapping("/{id}")
    //前端将在url中添加一个Integer类型的id，例如:localhost:9090/student/1，注意使用delete注解
    public Result delete(@PathVariable Integer id) {
        boolean flag = studentService.deleteById(id);
        return new Result(flag ? Code.DELETE_OK : Code.DELETE_ERR, flag);
    }

    @GetMapping("/{id}")
    //前端将在url中添加一个Integer类型的id，例如:localhost:9090/student/1，注意使用get注解
    public Result getById(@PathVariable Integer id) {
        Student student = studentService.selectById(id);
        Integer code = student != null ? Code.GET_OK : Code.GET_ERR;
        String msg = student != null ? "" : "数据查询失败，请重试！";
        return new Result(code, student, msg);
    }

    @GetMapping
    //前端直接访问localhost:9090/student，注意使用get注解
    public Result getAll() {
        List<Student> studentList = studentService.getAll();
        System.out.println(studentList);
        Integer code = studentList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = studentList != null ? "" : "数据查询失败，请重试！";
        return new Result(code, studentList, msg);
    }

    /**
     * @description: 将班级信息拿出来放班级表，在后端添加，为了没有多余班级
     */
    @PostMapping("/saveClass")
    public Result saveClasses() {
        List<StuClass> classes = studentService.getClasses();
        Integer code = classes != null ? Code.GET_OK : Code.GET_ERR;
        String msg = classes != null ? "班级保存成功!" : "班级保存错误，请重试！";
        return new Result(code, classes, msg);
        // 将classes保存到数据库中的班级表中
    }

    @GetMapping("/pageLike")
    //分页查询，参数均在请求体中，注意使用get注解
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String id, @RequestParam String name) {
        Map<String, Object> studentMap = studentService.selectLike(pageNum, pageSize, id, name);
        Integer code = studentMap != null ? Code.GET_OK : Code.GET_ERR;
        String msg = studentMap != null ? "" : "数据查询失败，请重试！";
        return new Result(code, studentMap, msg);
    }

}