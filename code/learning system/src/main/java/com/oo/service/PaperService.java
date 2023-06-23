package com.oo.service;

import com.oo.domain.Paper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:06
 */
@Transactional
public interface PaperService {
    /**
     * 通过id返回paper (name,totalscore)
     * 即查询试卷名字，总分
     *
     * @param id
     * @return paper
     */
    public Paper selectById(Integer id);

    public Integer selectcount(Integer id);



}
