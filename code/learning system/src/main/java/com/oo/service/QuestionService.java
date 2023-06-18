package com.oo.service;

import com.oo.domain.Question;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:07
 */
@Transactional
public interface QuestionService {

    public List<Question> getAll();

}
