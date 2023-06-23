package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.dao.StuClassDao;
import com.oo.dao.StudentDao;
import com.oo.domain.StuClass;
import com.oo.domain.Student;
import com.oo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private StuClassDao stuClassDao;


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


    /**
     * @description 获取班级且不能重复，并按id:?  name:? 格式返回
     * @return classes
     */
    @Override
    public List<StuClass> getClasses(){
        List<String> getClassName = studentDao.getAllClasses();
        System.out.println(getClassName);
        List<StuClass> classes = new ArrayList<>();
        for (String clazz : getClassName) {
            StuClass stuClass = new StuClass();
            stuClass.setClassName(clazz);
            stuClass.setId(getClassName.indexOf(clazz) + 1);
            System.out.println(stuClass);
            //一个一个保存数据库中
            if(stuClassDao.insert(stuClass)<0)
            {
                classes = null;
                return classes;
            }
        }
        classes = stuClassDao.selectList(null);

//        for (String clazz : getClassName) {
//            StuClass stuClass = new StuClass();
//            stuClass.setClassName(clazz);
//            stuClass.setId(getClassName.indexOf(clazz) + 1);
//            classes.add(stuClass);
//        }
        return classes;
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
