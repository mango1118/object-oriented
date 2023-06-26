package com.oo.service;

import com.oo.domain.SearchScoreDTO;
import com.oo.domain.SelectExamsDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:07
 */
@Transactional
public interface StudentPaperService {


    public List<SelectExamsDTO> selectLike(Integer pageNum, Integer pageSize, Integer paperId, String paperName, Integer id);

    public List<SearchScoreDTO> selectScore(Integer pageNum, Integer pageSize, Integer paperId, String paperName, Integer studentId);

    public Integer getobjectivescore(Integer paperid, Integer studentid, List<String> multipleChoiceAnswers, List<String> fillInTheBlankAnswers);

    public void savesubjectiveAnswers(Integer paperid, Integer studentid, List<String> subjectiveAnswers);
}
