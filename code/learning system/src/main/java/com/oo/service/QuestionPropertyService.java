package com.oo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.domain.QuestionProperty;
import com.oo.domain.QuestionSearchDTO;
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

    boolean update(QuestionVo questionVo);

    boolean save(QuestionProperty questionProperty);

    /**
     * 分页查询全部题目
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<QuestionVo> selectPageVo(Integer pageNum, Integer pageSize);


    //Page<QuestionVo> searchPageVo(QuestionSearchDTO questionSearchDTO, Integer pageNum, Integer pageSize);
    List<QuestionVo> searchPageVo(QuestionSearchDTO questionSearchDTO);

    /**
     * 根据性质查询题目 分页查询
     * @param pageNum
     * @param pageSize
     * @param questionId
     * @param type
     * @param knowledgePoint
     * @param chapter
     * @return
     */
//    Page<QuestionVo> searchPageVo(Integer pageNum,
//                                  Integer pageSize,
//                                  Integer questionId,
//                                  String type,
//                                  String knowledgePoint,
//                                  String chapter);
}
