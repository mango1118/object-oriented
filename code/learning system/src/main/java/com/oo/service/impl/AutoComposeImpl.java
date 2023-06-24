package com.oo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oo.dao.PaperDao;
import com.oo.dao.PaperQuestionDao;
import com.oo.dao.QuestionDao;
import com.oo.dao.QuestionPropertyDao;
import com.oo.domain.Paper;
import com.oo.domain.PaperQuestion;
import com.oo.domain.Question;
import com.oo.domain.QuestionProperty;
import com.oo.service.AutoComposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AutoComposeImpl implements AutoComposeService {

    @Autowired
    private PaperDao paperDao;
    @Autowired
    private PaperQuestionDao paperquestionDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionPropertyDao questionPropertyDao;

    @Override
    public List<Map<String, Object>> mapSearch(List<Map<String, Object>> list, String type, Integer level) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            //System.out.println(map.get("difficulty"));
            if (map.get("difficulty").equals(level) && map.get("type").equals(type)) {
                result.add(map);
            }
        }

        return result;
    }


    public Integer ACount(int[] array) {
        int c = 0;
        while (c < 8 && array[c] != 0) c++;
        return c;
    }

    public selectQuestion selectQ(List<Map<String, Object>> list) {
        int size = 0, index = 0, i = 0, id = 0;
        Map<Integer, Map<String, Object>> ran = new HashMap<>();
        for (Map<String, Object> entry : list) {
            ran.put(i++, entry);
        }
        size = list.size();
        index = (int) (Math.random() * (size - 1));
        Map<String, Object> map = ran.get(index);
        id = (int) map.get("question_id");
        list.remove(map);
        return new selectQuestion(list, id);
    }

    @Override
    public Integer autoCompose(String name, Integer total, Integer choice, Integer fillIn,
                               Integer subjective, Integer level) {
        Integer allQCount = choice + fillIn + subjective;
        int easy = 0, mid = 0, hard = 0;
        int c = choice, fi = fillIn, s = subjective;
        Integer id = 0;
        double choiceScore = 0, fillInScore = 0, subjectiveScore = 0;
        int[] choiceId = new int[8];
        int[] fillInId = new int[8];
        int[] subjectiveId = new int[8];
        Paper paper = new Paper();
        //KeyGenerator keyGenerator = new Jdbc3KeyGenerator();
        paper.setEnabled(1);
        paper.setName(name);
        paper.setTotalScore(total);
        int re = paperDao.insert(paper);
        if (re == 0) {
            System.out.println("插入paper失败。");
        }
        id = paper.getId();

        //根据试卷难易度，设置不同难度的题目比例
        if (level == 1) {
            hard = (int) ((double) allQCount * 0.2);
            mid = (int) ((double) allQCount * 0.4);
            easy = allQCount - hard - mid;
        } else if (level == 2) {
            easy = (int) ((double) allQCount * 0.3);
            hard = (int) ((double) allQCount * 0.2);
            mid = allQCount - easy - hard;
        } else if (level == 3) {
            easy = (int) ((double) allQCount * 0.2);
            mid = (int) ((double) allQCount * 0.5);
            hard = allQCount - easy - mid;
        }

        //设置每种题型的分值
        if (choice.equals(allQCount) || fillIn.equals(allQCount) || subjective.equals(allQCount)) {
            choiceScore = total / (double) allQCount;
            fillInScore = total / (double) allQCount;
            subjectiveScore = total / (double) allQCount;
        } else if (choice == 0) {
            fillInScore = total / (double) allQCount * 0.8;
            subjectiveScore = (total - fillInScore * mid) / subjective;
        } else if (fillIn == 0) {
            choiceScore = total / (double) allQCount * 0.8;
            subjectiveScore = (total - choiceScore * choice) / subjective;
        } else if (subjective == 0) {
            choiceScore = total / (double) allQCount * 0.8;
            fillInScore = (total - choiceScore * choice) / fillIn;
        } else {
            subjectiveScore = total / (double) allQCount * 1.2;
            fillInScore = total / (double) allQCount * 1;
            choiceScore = (total - subjectiveScore * subjective - fillInScore * fillIn) / choice;
        }

        //将题目表和题目性质表做个连接，取题目id，题目type，题目难度
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        QueryWrapper<QuestionProperty> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.select("id", "type").eq("deleted", 0);
        queryWrapper1.select("question_id", "difficulty");
        List<Map<String, Object>> type = new ArrayList<>();
        List<Map<String, Object>> diff = new ArrayList<>();
        type = questionDao.selectMaps(queryWrapper);
        diff = questionPropertyDao.selectMaps(queryWrapper1);
        //System.out.println(diff);
        /*
        Map<Integer, Map<String, Object>> typeMap = type.stream().collect(Collectors.toMap(
                m -> Integer.parseInt(m.get("id").toString()), m -> m));
        Map<Integer, Map<String, Object>> diffMap = diff.stream().collect(Collectors.toMap(
                m -> Integer.parseInt(m.get("question_id").toString()), m -> m));


        for (Map.Entry<Integer, Map<String, Object>> entry : diffMap.entrySet()) {
            Map<String, Object> map = entry.getValue();
            map.put("type", typeMap.get(entry.getKey()).get("type"));
            id_type_level.add(map);
        }*/

        List<Map<String, Object>> id_type_level = new ArrayList<>();
        for (Map<String, Object> diffMap : diff) {
            for (Map<String, Object> typeMap : type) {
                if ((diffMap.get("question_id")).equals(typeMap.get("id"))) {
                    diffMap.put("type", typeMap.get("type"));
                    id_type_level.add(diffMap);
                }
            }
        }
        type.clear();
        diff.clear();
        //System.out.println(id_type_level);

        //为试卷选题目
        int i = 0;
        if (choice.equals(allQCount)) {
            List<Map<String, Object>> selectEasy = mapSearch(id_type_level, "选择题", 1);
            //System.out.println(selectEasy);
            List<Map<String, Object>> selectMid = mapSearch(id_type_level, "选择题", 2);
            List<Map<String, Object>> selectHard = mapSearch(id_type_level, "选择题", 3);
            while (choice != 0) {
                while (easy != 0) {
                    selectQuestion Qid = selectQ(selectEasy);
                    selectEasy = Qid.getList();
                    choiceId[i++] = Qid.getId();
                    easy--;
                    choice--;
                }
                while (mid != 0) {
                    selectQuestion Qid = selectQ(selectMid);
                    selectMid = Qid.getList();
                    choiceId[i++] = Qid.getId();
                    mid--;
                    choice--;
                }
                while (hard != 0) {
                    selectQuestion Qid = selectQ(selectHard);
                    selectHard = Qid.getList();
                    choiceId[i++] = Qid.getId();
                    hard--;
                    choice--;
                }
            }
        } else if (fillIn.equals(allQCount)) {
            List<Map<String, Object>> selectEasy = mapSearch(id_type_level, "填空题", 1);
            List<Map<String, Object>> selectMid = mapSearch(id_type_level, "填空题", 2);
            List<Map<String, Object>> selectHard = mapSearch(id_type_level, "填空题", 3);
            while (fillIn != 0) {
                while (easy != 0) {
                    selectQuestion Qid = selectQ(selectEasy);
                    selectEasy = Qid.getList();
                    fillInId[i++] = Qid.getId();
                    easy--;
                    fillIn--;
                }
                while (mid != 0) {
                    selectQuestion Qid = selectQ(selectMid);
                    selectMid = Qid.getList();
                    fillInId[i++] = Qid.getId();
                    mid--;
                    fillIn--;
                }
                while (hard != 0) {
                    selectQuestion Qid = selectQ(selectHard);
                    selectHard = Qid.getList();
                    fillInId[i++] = Qid.getId();
                    hard--;
                    fillIn--;
                }
            }
        } else if (subjective.equals(allQCount)) {
            List<Map<String, Object>> selectEasy = mapSearch(id_type_level, "主观题", 1);
            List<Map<String, Object>> selectMid = mapSearch(id_type_level, "主观题", 2);
            List<Map<String, Object>> selectHard = mapSearch(id_type_level, "主观题", 3);
            while (subjective != 0) {
                while (easy != 0) {
                    selectQuestion Qid = selectQ(selectEasy);
                    selectEasy = Qid.getList();
                    subjectiveId[i++] = Qid.getId();
                    easy--;
                    subjective--;
                }
                while (mid != 0) {
                    selectQuestion Qid = selectQ(selectMid);
                    selectMid = Qid.getList();
                    subjectiveId[i++] = Qid.getId();
                    mid--;
                    subjective--;
                }
                while (hard != 0) {
                    selectQuestion Qid = selectQ(selectHard);
                    selectHard = Qid.getList();
                    subjectiveId[i++] = Qid.getId();
                    hard--;
                    subjective--;
                }
            }
        } else if (choice == 0) {
            int f = 0, sub = 0;
            List<Map<String, Object>> selectSubEasy = mapSearch(id_type_level, "主观题", 1);
            List<Map<String, Object>> selectFillEasy = mapSearch(id_type_level, "填空题", 1);
            while (easy != 0) {
                if (fillIn != 0) {
                    selectQuestion Qid = selectQ(selectFillEasy);
                    selectFillEasy = Qid.getList();
                    fillInId[f++] = Qid.getId();
                    fillIn--;
                    easy--;
                }
                if (easy != 0 && subjective != 0) {
                    selectQuestion Qid = selectQ(selectSubEasy);
                    selectSubEasy = Qid.getList();
                    subjectiveId[sub++] = Qid.getId();
                    subjective--;
                    easy--;
                }
            }
            List<Map<String, Object>> selectSubMid = mapSearch(id_type_level, "主观题", 2);
            List<Map<String, Object>> selectFillMid = mapSearch(id_type_level, "填空题", 2);
            while (mid != 0) {
                if (fillIn != 0) {
                    selectQuestion Qid = selectQ(selectFillMid);
                    selectFillMid = Qid.getList();
                    fillInId[f++] = Qid.getId();
                    fillIn--;
                    mid--;
                }
                if (mid != 0 && subjective != 0) {
                    selectQuestion Qid = selectQ(selectSubMid);
                    selectSubMid = Qid.getList();
                    subjectiveId[sub++] = Qid.getId();
                    subjective--;
                    mid--;
                }
            }
            List<Map<String, Object>> selectSubHard = mapSearch(id_type_level, "主观题", 3);
            List<Map<String, Object>> selectFillHard = mapSearch(id_type_level, "填空题", 3);
            while (hard != 0) {
                if (fillIn != 0) {
                    selectQuestion Qid = selectQ(selectFillHard);
                    selectFillHard = Qid.getList();
                    fillInId[f++] = Qid.getId();
                    fillIn--;
                    hard--;
                }
                if (hard != 0 && subjective != 0) {
                    selectQuestion Qid = selectQ(selectSubHard);
                    selectSubHard = Qid.getList();
                    subjectiveId[sub++] = Qid.getId();
                    subjective--;
                    hard--;
                }
            }

        } else if (fillIn == 0) {
            int ch = 0, sub = 0;
            List<Map<String, Object>> selectChoiceEasy = mapSearch(id_type_level, "选择题", 1);
            List<Map<String, Object>> selectSubEasy = mapSearch(id_type_level, "主观题", 1);
            while (easy != 0) {
                if (choice != 0) {
                    selectQuestion Qid = selectQ(selectChoiceEasy);
                    selectChoiceEasy = Qid.getList();
                    choiceId[ch++] = Qid.getId();
                    choice--;
                    easy--;
                }
                if (easy != 0 && subjective != 0) {
                    selectQuestion Qid = selectQ(selectSubEasy);
                    selectSubEasy = Qid.getList();
                    subjectiveId[sub++] = Qid.getId();
                    subjective--;
                    easy--;
                }
            }
            List<Map<String, Object>> selectChoiceMid = mapSearch(id_type_level, "选择题", 2);
            List<Map<String, Object>> selectSubMid = mapSearch(id_type_level, "主观题", 2);
            while (mid != 0) {
                if (choice != 0) {
                    selectQuestion Qid = selectQ(selectChoiceMid);
                    selectChoiceMid = Qid.getList();
                    choiceId[ch++] = Qid.getId();
                    choice--;
                    mid--;
                }
                if (mid != 0 && subjective != 0) {
                    selectQuestion Qid = selectQ(selectSubMid);
                    selectSubMid = Qid.getList();
                    subjectiveId[sub++] = Qid.getId();
                    subjective--;
                    mid--;
                }
            }
            List<Map<String, Object>> selectChoiceHard = mapSearch(id_type_level, "选择题", 3);
            List<Map<String, Object>> selectSubHard = mapSearch(id_type_level, "主观题", 3);
            while (hard != 0) {
                if (choice != 0) {
                    selectQuestion Qid = selectQ(selectChoiceHard);
                    selectChoiceHard = Qid.getList();
                    choiceId[ch++] = Qid.getId();
                    choice--;
                    hard--;
                }
                if (hard != 0 && subjective != 0) {
                    selectQuestion Qid = selectQ(selectSubHard);
                    selectSubHard = Qid.getList();
                    subjectiveId[sub++] = Qid.getId();
                    subjective--;
                    hard--;
                }
            }
        } else if (subjective == 0) {
            int ch = 0, f = 0;
            List<Map<String, Object>> selectChoiceEasy = mapSearch(id_type_level, "选择题", 1);
            List<Map<String, Object>> selectFillEasy = mapSearch(id_type_level, "填空题", 1);
            while (easy != 0) {
                if (choice != 0) {
                    selectQuestion Qid = selectQ(selectChoiceEasy);
                    selectChoiceEasy = Qid.getList();
                    choiceId[ch++] = Qid.getId();
                    choice--;
                    easy--;
                }
                if (easy != 0 && fillIn != 0) {
                    selectQuestion Qid = selectQ(selectFillEasy);
                    selectFillEasy = Qid.getList();
                    fillInId[f++] = Qid.getId();
                    fillIn--;
                    easy--;
                }
            }
            List<Map<String, Object>> selectChoiceMid = mapSearch(id_type_level, "选择题", 2);
            List<Map<String, Object>> selectFillMid = mapSearch(id_type_level, "填空题", 2);
            while (mid != 0) {
                if (choice != 0) {
                    selectQuestion Qid = selectQ(selectChoiceMid);
                    selectChoiceMid = Qid.getList();
                    choiceId[ch++] = Qid.getId();
                    choice--;
                    mid--;
                }
                if (mid != 0 && fillIn != 0) {
                    selectQuestion Qid = selectQ(selectFillMid);
                    selectFillMid = Qid.getList();
                    fillInId[f++] = Qid.getId();
                    fillIn--;
                    mid--;
                }
            }
            List<Map<String, Object>> selectChoiceHard = mapSearch(id_type_level, "选择题", 3);
            List<Map<String, Object>> selectFillHard = mapSearch(id_type_level, "填空题", 3);
            while (hard != 0) {
                if (choice != 0) {
                    selectQuestion Qid = selectQ(selectChoiceHard);
                    selectChoiceHard = Qid.getList();
                    choiceId[ch++] = Qid.getId();
                    choice--;
                    hard--;
                }
                if (hard != 0 && fillIn != 0) {
                    selectQuestion Qid = selectQ(selectFillHard);
                    selectFillHard = Qid.getList();
                    fillInId[f++] = Qid.getId();
                    fillIn--;
                    hard--;
                }
            }
        } else {
            int ch = 0, f = 0, sub = 0;
            List<Map<String, Object>> selectChoiceEasy = mapSearch(id_type_level, "选择题", 1);
            List<Map<String, Object>> selectFillEasy = mapSearch(id_type_level, "填空题", 1);
            List<Map<String, Object>> selectSubEasy = mapSearch(id_type_level, "主观题", 1);
            while (0 != easy) {
                if (choice != 0) {
                    selectQuestion Qid = selectQ(selectChoiceEasy);
                    selectChoiceEasy = Qid.getList();
                    choiceId[ch++] = Qid.getId();
                    choice--;
                    easy--;
                }
                if (easy != 0 && fillIn != 0) {
                    selectQuestion Qid = selectQ(selectFillEasy);
                    selectFillEasy = Qid.getList();
                    fillInId[f++] = Qid.getId();
                    fillIn--;
                    easy--;
                }
                if (easy != 0 && subjective != 0) {
                    selectQuestion Qid = selectQ(selectSubEasy);
                    selectSubEasy = Qid.getList();
                    subjectiveId[sub++] = Qid.getId();
                    subjective--;
                    easy--;
                }
            }
            List<Map<String, Object>> selectChoiceMid = mapSearch(id_type_level, "选择题", 2);
            List<Map<String, Object>> selectFillMid = mapSearch(id_type_level, "填空题", 2);
            List<Map<String, Object>> selectSubMid = mapSearch(id_type_level, "主观题", 2);
            while (0 != mid) {
                if (fillIn != 0) {
                    selectQuestion Qid = selectQ(selectFillMid);
                    selectFillMid = Qid.getList();
                    fillInId[f++] = Qid.getId();
                    fillIn--;
                    mid--;
                }
                if (mid != 0 && choice != 0) {
                    selectQuestion Qid = selectQ(selectChoiceMid);
                    selectChoiceMid = Qid.getList();
                    choiceId[ch++] = Qid.getId();
                    choice--;
                    mid--;
                }
                if (mid != 0 && subjective != 0) {
                    selectQuestion Qid = selectQ(selectSubMid);
                    selectSubMid = Qid.getList();
                    subjectiveId[sub++] = Qid.getId();
                    subjective--;
                    mid--;
                }
            }
            List<Map<String, Object>> selectChoiceHard = mapSearch(id_type_level, "选择题", 3);
            List<Map<String, Object>> selectFillHard = mapSearch(id_type_level, "填空题", 3);
            List<Map<String, Object>> selectSubHard = mapSearch(id_type_level, "主观题", 3);
            while (0 != hard) {
                if (subjective != 0) {
                    selectQuestion Qid = selectQ(selectSubHard);
                    selectSubHard = Qid.getList();
                    subjectiveId[sub++] = Qid.getId();
                    subjective--;
                    hard--;
                }
                if (hard != 0 && fillIn != 0) {
                    selectQuestion Qid = selectQ(selectFillHard);
                    selectFillHard = Qid.getList();
                    fillInId[f++] = Qid.getId();
                    fillIn--;
                    hard--;
                }
                if (hard != 0 && choice != 0) {
                    selectQuestion Qid = selectQ(selectChoiceHard);
                    selectChoiceHard = Qid.getList();
                    choiceId[ch++] = Qid.getId();
                    choice--;
                    hard--;
                }
            }
        }

        //将抽取的题目存进数据库表paper_question
        PaperQuestion PQ = new PaperQuestion();
        int chS = (int) Math.round(choiceScore);
        int fillS = (int) Math.round(fillInScore);
        int subS = (int) Math.round(subjectiveScore);
        int size1 = ACount(choiceId);
        int size2 = ACount(fillInId);
        int size3 = ACount(subjectiveId);
        int count = 0;
        int bia = total - chS * c - fillS * fi - subS * s;
        System.out.println(bia);
        for (int Qid : choiceId) {
            if (Qid == 0) break;
            if (fillInId[0] == 0 && subjectiveId[0] == 0 && count == (size1 - 1)) {
                PQ.setSetScore(chS + bia);
            } else {
                PQ.setSetScore(chS);
            }
            PQ.setPaperId(id);
            PQ.setQuestionId(Qid);
            int res = paperquestionDao.insert(PQ);
            count++;
            if (res == 0) {
                System.out.println("插入选择题失败。");
            }
        }
        count = 0;
        for (int Qid : fillInId) {
            if (Qid == 0) break;
            if (subjectiveId[0] == 0 && count == (size2 - 1)) {
                PQ.setSetScore(fillS + bia);
                System.out.println(bia);
            } else {
                PQ.setSetScore(fillS);
            }
            PQ.setPaperId(id);
            PQ.setQuestionId(Qid);
            int res = paperquestionDao.insert(PQ);
            count++;
            if (res == 0) {
                System.out.println("插入填空题失败。");
            }
        }
        count = 0;
        for (int Qid : subjectiveId) {
            if (Qid == 0) break;
            if (count == (size3 - 1)) {
                PQ.setSetScore(subS + bia);
            } else {
                PQ.setSetScore(subS);
            }
            PQ.setPaperId(id);
            PQ.setQuestionId(Qid);
            count++;
            int res = paperquestionDao.insert(PQ);
            if (res == 0) {
                System.out.println("插入主观题失败。");
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return id;
    }
}

class selectQuestion {
    public Integer id;
    public List<Map<String, Object>> list;

    public selectQuestion() {
    }

    public selectQuestion(List<Map<String, Object>> list, Integer id) {
        this.id = id;
        this.list = list;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public Integer getId() {
        return id;
    }
}