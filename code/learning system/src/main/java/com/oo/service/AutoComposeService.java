package com.oo.service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Transactional
public interface AutoComposeService {

    //用于map条件查询
    public List<Map<String, Object>> mapSearch(List<Map<String, Object>> list, String type, Integer level);


    /**
     * 通过FormData(试卷参数设置表单)返回paper (id)
     *
     * @return id
     */
    public Integer autoCompose(String name,  Integer total, Integer Choice, Integer fillIn,
                               Integer subjective,  Integer level);

}
