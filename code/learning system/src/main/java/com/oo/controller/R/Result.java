package com.oo.controller.R;
import com.oo.domain.vo.Qcontent_score;
import com.oo.domain.Paper;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @description: 结果包装类
 * @author: mango
 * @date: 2023/5/26 15:42
 */
public class Result {
    private Object data;
    private Integer code;
    private String msg;

    public Result() {
    }

    public Result(Integer code,Object data) {
        this.data = data;
        this.code = code;
    }

    public Result(Integer code, Object data, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code,Paper paper, Map<String, Object> paperQ,  String msg) {

        this.code = code;
        this.msg = msg;
        examPaper examPaper = new examPaper(paper);
        //Map<String, Object> data = new HashMap<>();
        //data.put("data", paperQ);
        //data.put("total", total);
        paperQ.put("examPaper", examPaper);
        this.data = paperQ;

    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}

class examPaper {
    private Integer id;
    private String name;
    private Integer totalScore;

    public examPaper( ){
    }
    public examPaper( Paper examPaper) {
        this.id = examPaper.getId();
        this.totalScore = examPaper.getScore();
        this.name = examPaper.getName();
    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public Integer getTotalScore() {
        return totalScore;
    }
}

