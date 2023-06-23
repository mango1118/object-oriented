package com.oo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@title
 *@author bxt
 *@description
 *@create 2023/6/23 17:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchScoreDTO {
    Integer StudentId;
    String StudentName;
    Integer ExamId;
    String PaperName;
    Integer Score;
}
