package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.dao.PaperDao;
import com.oo.dao.StudentDao;
import com.oo.domain.Paper;
import com.oo.domain.Question;
import com.oo.domain.Student;
import com.oo.service.ManualComposeService;
import com.oo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ManualComposeServiceImpl implements ManualComposeService {

    @Autowired
    private PaperDao paperDao;

    @Override
    public boolean createPaper(Paper paper) {
        return paperDao.insert(paper) > 0;
    }

    @Override
    public boolean addQuestions(Long id, List<Question> paperQuestions) {
        return false;
    }


}
