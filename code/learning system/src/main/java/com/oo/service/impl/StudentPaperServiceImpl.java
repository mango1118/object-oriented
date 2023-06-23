package com.oo.service.impl;

import com.oo.dao.PaperDao;
import com.oo.dao.StudentDao;
import com.oo.dao.StudentPaperDao;
import com.oo.domain.Student;
import com.oo.domain.StudentPaper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oo.service.StudentPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:09
 */
@Service
public class StudentPaperServiceImpl implements StudentPaperService {

    @Autowired
    //dao的使用方法请学习mybatis-plus
    private StudentPaperDao studentPaperDao;
    private PaperDao paperDao;

    @Override
    public Map<String, Object> selectLike(Integer current, Integer size, String id, String examName) {
        Page<StudentPaper> page = new Page<>(current, size);
        LambdaQueryWrapper<StudentPaper> studentPaperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer paperId = paperDao.selectIdByName(examName);
        studentPaperLambdaQueryWrapper.like(null != id && !"null".equals(id), StudentPaper::getId, id);

        studentPaperLambdaQueryWrapper.like(null != paperId && !"null".equals(paperId), StudentPaper::getPaperId, paperId);

        List<StudentPaper> data = studentPaperDao.selectPage(page, studentPaperLambdaQueryWrapper).getRecords();
        Integer total = (int) studentPaperDao.selectPage(page, studentPaperLambdaQueryWrapper).getTotal();

        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);

        return res;
    }
}
