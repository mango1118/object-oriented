package com.oo.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:07
 */
@Transactional
public interface StudentPaperService {

    public Map<String, Object> selectLike(Integer current, Integer size, String id, String examName);
}
