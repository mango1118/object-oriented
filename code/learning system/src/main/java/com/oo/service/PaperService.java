package com.oo.service;

import org.springframework.transaction.annotation.Transactional;
import com.oo.domain.Paper;
import com.oo.domain.vo.Qcontent_score;
import java.util.List;
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
     * @param id
     * @return paper
     */
    public Paper selectById(Integer id);

    /**
     * 通过paper_id返回paper(question_content,score)
     * 即查询试卷每道题干，分值
     * @param id
     * @return List<Qcontent_score>
     */
    public List<Qcontent_score> selectByPaperId(Integer id);

}
