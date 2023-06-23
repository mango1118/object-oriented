package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oo.dao.ClassPaperDao;
import com.oo.dao.StuClassDao;
import com.oo.dao.StudentDao;
import com.oo.dao.TeacherDao;
import com.oo.domain.*;
import com.oo.service.TeacherService;
import com.sun.org.apache.xerces.internal.impl.xs.util.StringListImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 教师功能-----发卷、改卷
 * @author: mango
 * @date: 2023/5/26 16:09
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private StuClassDao stuClassDao;

    @Autowired
    private ClassPaperDao classPaperDao;

    /**
     * @description: 选择一个班级发卷-----更改sent属性
     */

    public boolean chooseClass(Integer paperId, Integer classId){
        QueryWrapper<ClassPaper> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", paperId).eq("class_id", classId);
        ClassPaper classPaper = classPaperDao.selectOne(wrapper);
        if(classPaper == null)
        {
            return false;
        }
        classPaper.setSent(true);
        return classPaperDao.updateById(classPaper) > 0;
    }

    /**
     * @description: 显示所有班级信息
     */
    public List<StuClass> getClasses(){
        List<StuClass> getClasses = stuClassDao.selectList(null);
        return getClasses;
    }

    /**
     * @description: 显示选择的班级的所有可用试卷信息
     */
    public List <Map<String,Object>> getTheClassPapers(Integer classId){
        System.out.println(classId);
        List <Map<String,Object>> classPapers = classPaperDao.findExamPapersByClassId(classId);
        System.out.println(classPapers);
        return classPapers;
    }








}
