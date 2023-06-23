package com.oo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@title
 *@author bxt
 *@description
 *@create 2023/6/21 17:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceQuestionsDTO {
    private Integer id;
    private String question;
}
