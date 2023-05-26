package com.oo.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 正确答案视图类
 * @author: mango
 * @date: 2023/5/26 15:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperCorrectAnswer {

    private Integer questionId;
    private Integer correctAnswer;

}
