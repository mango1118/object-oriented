package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oo.dao.*;
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

    @Autowired
    private StudentPaperDao studentPaperDao;

    /**
     * @description: 选择一个班级发卷-----更改sent属性;为了后续方便当发卷成功时将班级发到的卷子也存到学生试卷表中
     */

    public boolean chooseClass(Integer paperId, Integer classId){
        QueryWrapper<ClassPaper> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", paperId).eq("class_id", classId);
        ClassPaper classPaper = classPaperDao.selectOne(wrapper);
        if(classPaper == null)
        {
            return false;
        }
        boolean sentPaper = false;
        boolean savePaperId = false;
        classPaper.setSent(true);
        if(classPaperDao.updateById(classPaper)>0)
            sentPaper = true;
        if(sentPaper==true)
        {
            StuClass theClass = stuClassDao.selectById(classId);
            String className = theClass.getClassName();
            List<Integer> getAllStudentId = studentDao.getAllStudentId(className);
            System.out.println(getAllStudentId);
            for (Integer studentId : getAllStudentId) {
                savePaperId = false;
                StudentPaper studentPaper = new StudentPaper();
                studentPaper.setPaperId(paperId);
                studentPaper.setStudentId(studentId);
                System.out.println(studentPaper);
                //一个一个保存数据库中
                if(studentPaperDao.insert(studentPaper)<0)
                {
                    savePaperId = true;
                }
            }
        }
        return  sentPaper && savePaperId;
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
