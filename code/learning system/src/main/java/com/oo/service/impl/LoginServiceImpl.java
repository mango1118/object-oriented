package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oo.dao.AdminDao;
import com.oo.dao.StudentDao;
import com.oo.dao.TeacherDao;
import com.oo.domain.Admin;
import com.oo.domain.LoginDTO;
import com.oo.domain.Student;
import com.oo.domain.Teacher;
import com.oo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:08
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;
    /**
     * 增加管理员登录
     */
    @Autowired
    AdminDao adminDao;

    @Override
    public Object login(LoginDTO loginDTO) {
        String account = loginDTO.getAccount();
        String password = loginDTO.getPassword();

        boolean isStudent = false;
        boolean isTeacher = false;
        boolean isAdmin = false;

        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getAccount, account);
        studentLambdaQueryWrapper.eq(Student::getPassword, password);
        isStudent = null != studentDao.selectOne(studentLambdaQueryWrapper);

        LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherLambdaQueryWrapper.eq(Teacher::getAccount, account);
        teacherLambdaQueryWrapper.eq(Teacher::getPassword, password);
        isTeacher = null != teacherDao.selectOne(teacherLambdaQueryWrapper);

        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(Admin::getAccount, account);
        adminLambdaQueryWrapper.eq(Admin::getPassword, password);
        isAdmin = null != adminDao.selectOne(adminLambdaQueryWrapper);

        if (isStudent) {
            return studentDao.selectOne(studentLambdaQueryWrapper);
        } else if (isTeacher) {
            return teacherDao.selectOne(teacherLambdaQueryWrapper);
        }  else if (isAdmin) {
            return adminDao.selectOne(adminLambdaQueryWrapper);
        } else {
            return null;
        }

    }
}
