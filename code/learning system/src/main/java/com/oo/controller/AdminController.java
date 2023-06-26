package com.oo.controller;


import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.Admin;
import com.oo.domain.Student;
import com.oo.domain.Teacher;
import com.oo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    //通过spring自动注入
    private AdminService adminService;

    /**
     * 管理员修改信息
     */
    @PutMapping("/updateSelf/{id}")
    //前端将在请求体中发送一个Teacher类型的实例，注意使用put注解
    public Result update(@RequestBody Admin admin) {
        boolean flag = adminService.modifyAdmin(admin);
        return new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag);
    }

    /**
     * 获取管理员信息
     */
    @GetMapping("/{id}")
    //前端将在url中添加一个Integer类型的id，例如:localhost:9090/student/1，注意使用get注解
    public Result getAdminById(@PathVariable Integer id) {
        Admin admin = adminService.selectById(id);
        Integer code = admin != null ? Code.GET_OK : Code.GET_ERR;
        String msg = admin != null ? "" : "数据查询失败，请重试！";
        return new Result(code, admin, msg);
    }


    /**
     * 新增教师新息
     */
    @PostMapping("/teacherControl")
    //前端将在请求体中发送一个Teacher类型的实例，注意使用post注解
    public Result save(@RequestBody Teacher teacher) {
        //System.out.println(teacher);
        boolean flag = adminService.addTeacher(teacher);
        return new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag);
    }



    /**
     * 按照id修改教师新息
     */
    @PutMapping("/teacherControl/{id}")
    //前端将在请求体中发送一个Teacher类型的实例，注意使用put注解
    public Result update(@RequestBody Teacher teacher) {
        boolean flag = adminService.modifyTeacher(teacher);
        return new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag);
    }


    /**
     * 删除教师
     */
    @DeleteMapping("/teacherControl/{id}")
    //前端将在url中添加一个Integer类型的id，例如:localhost:9090/admin/teacherControl/1，注意使用delete注解
    public Result delete(@PathVariable Integer id) {
        boolean flag = adminService.deleteTeacherById(id);
        return new Result(flag ? Code.DELETE_OK : Code.DELETE_ERR, flag);
    }



    /**
     * 根据id搜索教师信息
     */
    @GetMapping("/teacherControl/{id}")
    //前端将在url中添加一个Integer类型的id，例如:localhost:9090/admin/teacherControl/1，注意使用get注解
    public Result getById(@PathVariable Integer id) {
        Teacher teacher = adminService.getTeacherById(id);
        Integer code = teacher != null ? Code.GET_OK : Code.GET_ERR;
        String msg = teacher != null ? "" : "数据查询失败，请重试！";
        return new Result(code, teacher, msg);
    }



    /**
     * 获取教师所有新息
     */
    @GetMapping("/teacherControl")
    //前端直接访问localhost:9090/admin/teacherControl，注意使用get注解
    public Result getAll() {
        List<Teacher> teacherList = adminService.getAll();
        Integer code = teacherList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = teacherList != null ? "" : "数据查询失败，请重试！";
        return new Result(code, teacherList, msg);
    }


    /**
     * 分页模糊查询教师新息
     */
    @GetMapping("/teacherControl/pageLike")
    //分页查询，参数均在请求体中，注意使用get注解
    public Result findPageSearch(@RequestParam(defaultValue = "1")  Integer pageNum,
                                 @RequestParam(defaultValue = "5")  Integer pageSize,
                                 @RequestParam String account,
                                 @RequestParam String name) {
        Map<String, Object> teacherMap = adminService.selectLike(pageNum, pageSize, account, name);
        Integer code = teacherMap != null ? Code.GET_OK : Code.GET_ERR;
        String msg = teacherMap != null ? "" : "数据查询失败，请重试！";
        return new Result(code, teacherMap, msg);
    }

}
