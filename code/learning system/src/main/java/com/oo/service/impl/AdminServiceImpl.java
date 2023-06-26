package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.dao.AdminDao;
import com.oo.domain.Admin;
import com.oo.domain.Student;
import com.oo.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import com.oo.dao.TeacherDao;
import com.oo.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 教师管理---增删改查
 * @author: SiQi33
 * @date: 2023/5/26 16:09
 */

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private TeacherDao teacherDao;


    @Autowired
    private AdminDao adminDao;


    /**
     * @description: 增加教师
     */
    public boolean addTeacher(Teacher teacher){
        return teacherDao.insert(teacher)>0;
    }

    /**
     * @description: 根据id删除教师
     */
    public boolean deleteTeacherById(Integer id){
        return teacherDao.deleteById(id)>0;
    }

    /**
     * @description: 修改教师信息
     */

    public boolean modifyTeacher(Teacher teacher){
        return teacherDao.updateById(teacher)>0;
    }

    /**
     * @description: 根据id查找教师信息
     */
    public Teacher getTeacherById(Integer id){
        return teacherDao.selectById(id);
    }

    /**
     * @description: 查找所有教师信息
     */
    public List<Teacher> getAll(){
        return teacherDao.selectList(null);
    }



    /**
     * 分页模糊查询教师新息
     */
    @Override
    public Map<String, Object> selectLike(Integer current, Integer size, String account, String name) {
        Page<Teacher> page = new Page<>(current, size);
        LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherLambdaQueryWrapper.like(StringUtils.isNotBlank(account), Teacher::getAccount, account);
        teacherLambdaQueryWrapper.like(StringUtils.isNotBlank(name), Teacher::getName, name);

        List<Teacher> data = teacherDao.selectPage(page, teacherLambdaQueryWrapper).getRecords();
        Integer total = (int) teacherDao.selectPage(page, teacherLambdaQueryWrapper).getTotal();

        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);

        return res;
    }


    /**
     * @description: 修改管理员信息
     */

    public boolean modifyAdmin(Admin admin){
        return adminDao.updateById(admin)>0;
    }

    /**
     * @description: 获取管理员信息
     */


    public Admin selectById(Integer id) {

        return adminDao.selectById(id);
    }
}
