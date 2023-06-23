package com.oo.service;

import com.oo.domain.Question;
import com.oo.domain.StuClass;
import com.oo.domain.Student;
import com.oo.domain.Teacher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:07
 */
@Transactional
public interface TeacherService {
    //    /**
//     * @description: 根据试卷名从试卷中找到对应试卷id
//     */
//    public Integer selectIdByName(String name);

    //    public Integer selectIdByName(String name){
//
//        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name", name);
//        Paper paper = paperDao.selectOne(queryWrapper);
//        Integer paperId = paper.getId();
//        return paperId;
//    }

    public boolean chooseClass(Integer paperId, Integer classId);

    public List<StuClass> getClasses();

    public List <Map<String,Object>> getTheClassPapers(Integer classId);

}
