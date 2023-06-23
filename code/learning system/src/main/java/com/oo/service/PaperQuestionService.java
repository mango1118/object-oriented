package com.oo.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:07
 */
@Transactional
public interface PaperQuestionService {

    /**
     * 删除student
     *
     * @param id
     * @return List<Integer>
     */
    public List<Integer> getQuestionsByPaperId(Integer id);
}
