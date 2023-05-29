package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.dao.StudentDao;
import com.oo.domain.Student;
import com.oo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: Dao的使用方法请学习mybatis-plus
 * @author: mango
 * @date: 2023/5/26 15:34
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    //dao的使用方法请学习mybatis-plus
    private StudentDao studentDao;

    @Override
    public boolean save(Student student) {
        return studentDao.insert(student) > 0;
    }

    @Override
    public boolean update(Student student) {
        return studentDao.updateById(student) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return studentDao.deleteById(id) > 0;
    }

    @Override
    public Student selectById(Integer id) {
        return studentDao.selectById(id);
    }

    @Override
    public List<Student> getAll() {
        return studentDao.selectList(null);
    }

    @Override
    public Map<String, Object> selectLike(Integer current, Integer size, String id, String name) {
        Page<Student> page = new Page<>(current, size);
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.like(null != id && !"null".equals(id), Student::getId, id);
        studentLambdaQueryWrapper.like(null != name && !"null".equals(name), Student::getName, name);

        List<Student> data = studentDao.selectPage(page, studentLambdaQueryWrapper).getRecords();
        Integer total = (int) studentDao.selectPage(page, studentLambdaQueryWrapper).getTotal();

        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);

        return res;
    }

}
