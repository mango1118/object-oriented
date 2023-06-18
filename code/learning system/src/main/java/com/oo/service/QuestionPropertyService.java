package com.oo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.domain.Question;
import com.oo.domain.QuestionProperty;
import com.oo.domain.vo.QuestionVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:07
 */
@Transactional
public interface QuestionPropertyService {


    QuestionProperty selectById(Integer id);

    boolean update(QuestionProperty questionProperty);

    boolean save(QuestionProperty questionProperty);

    /**
     * 分页查询全部题目
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<QuestionVo> selectPageVo(Integer pageNum, Integer pageSize);
}
