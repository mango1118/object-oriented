package com.oo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 试卷内容分值视图类
 * @author: lzq
 * @date: 2023/6/11 19:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Qcontent_score {

    private String content;
    private Integer score;
    private String type;
}
