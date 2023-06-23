package com.oo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.*;
import com.oo.domain.vo.QuestionVo;
import com.oo.service.QuestionPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:30
 */
@RestController
@RequestMapping("/questionProperties")
public class QuestionPropertyController {
    @Autowired
    //通过spring自动注入
    private QuestionPropertyService questionPropertyService;
    @Value("${files.upload.path}")
    // 写在resources/application.yml中
    private String fileUploadPath;

    /**
     * 分页查询全部题目
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/pageLike")
    //前端将在url中添加一个Integer类型的id，例如:localhost:9090/papers/1，注意使用get注解
    public Result getById(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5") Integer pageSize) {


        Page<QuestionVo> questionVoPage= questionPropertyService.selectPageVo(pageNum, pageSize);

        Integer code = questionVoPage != null && questionVoPage != null ? Code.GET_OK : Code.GET_ERR;
        String msg = questionVoPage != null && questionVoPage != null ? "" : "数据查询失败，请重试！";
        //System.out.println(questionVoPage.getRecords());
        return new Result(code, questionVoPage.getRecords(), msg);
    }


//    /**
//     * 查询某个问题的性质：1
//     * @param id
//     * @return
//     */
//    @GetMapping("/{id}")
//    public Result getById(@PathVariable Integer id) {
//        QuestionProperty questionProperty = questionPropertyService.selectById(id);
//        Integer code = questionProperty != null ? Code.GET_OK : Code.GET_ERR;
//        String msg = questionProperty != null ? "" : "数据查询失败，请重试！";
//        return new Result(code, questionProperty, msg);
//    }

    /**
     * 编辑题目性质
     * @param questionVo
     * @return
     */
    @PutMapping
    public Result update(@RequestBody QuestionVo questionVo) {
        boolean flag = questionPropertyService.update(questionVo);
        Integer code = flag == true ? Code.GET_OK : Code.GET_ERR;
        String msg = flag==true ? "编辑成功" : "编辑失败，请重试！";
        return new Result(code,msg);
    }


//    @GetMapping
//    public Result search(@RequestParam(defaultValue = "1") Integer pageNum,
//                         @RequestParam(defaultValue = "5") Integer pageSize,
//                         @RequestParam Integer questionId,
//                         @RequestParam String type,
//                         @RequestParam String knowledgePoint,
//                         @RequestParam String chapter
//                         ){
//        System.out.println(pageNum);
//        System.out.println(pageSize);
//        System.out.println( questionId+"===="+type+"======"+knowledgePoint+"========"+chapter);
//
//        Page<QuestionVo> questionVoPage= questionPropertyService.searchPageVo(pageNum, pageSize,questionId,type,knowledgePoint,chapter);
//
//        Integer code = questionVoPage != null && questionVoPage != null ? Code.GET_OK : Code.GET_ERR;
//        String msg = questionVoPage != null && questionVoPage != null ? "" : "数据查询失败，请重试！";
//        //System.out.println(questionVoPage.getRecords());
//        return new Result(code, questionVoPage.getRecords(), msg);
//
//    }
    @GetMapping("/search")
    public Result search(@RequestParam(defaultValue = "1") Integer pageNum,
                         @RequestParam(defaultValue = "5") Integer pageSize,
                         @RequestParam(required = false) Integer questionId,
                         @RequestParam(required = false) String type,
                         @RequestParam(required = false) String knowledgePoint,
                         @RequestParam(required = false) String chapter
    ){
//        System.out.println(pageNum);
//        System.out.println(pageSize);
//        System.out.println( questionId+"===="+type+"======"+knowledgePoint+"========"+chapter);
        QuestionSearchDTO questionSearchDTO = new QuestionSearchDTO();
//        if(!"null".equals(questionId) && !questionId.equals("")){
//            Integer questionId2 = Integer.valueOf(questionId);
//            questionSearchDTO.setQuestionId(questionId2);
//        }
        //前端好像不能直接传个null回后端
        if (questionId==0){
            questionId=null;
        }
        //前端传type为null的时候好像传了个带空号的“null”字符串过来
        if(type.trim().equals("null")){
            type=null;
        }else{
            type = type.trim();
        }
        if(knowledgePoint.equals("null")){
            knowledgePoint=null;
        }
        if(chapter.equals("null")){
            chapter=null;
        }
        //封装为questionSearchDTO
        questionSearchDTO.setQuestionId(questionId);
        questionSearchDTO.setChapter(chapter);
        questionSearchDTO.setType(type);
        questionSearchDTO.setKnowledgePoint(knowledgePoint);
        questionSearchDTO.setOffset((pageNum-1)*pageSize);
        questionSearchDTO.setSize(pageSize);
        System.out.println(questionSearchDTO);
        //Page<QuestionVo> questionVoPage= questionPropertyService.searchPageVo(questionSearchDTO,pageNum, pageSize);
        List<QuestionVo> questionVoList= questionPropertyService.searchPageVo(questionSearchDTO);
        Integer code = questionVoList != null && questionVoList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = questionVoList != null && questionVoList != null ? "" : "数据查询失败，请重试！";
        //System.out.println(questionVoPage.getRecords());
        return new Result(code, questionVoList, msg);

    }






//    /**
//     * 设置问题性质
//     * @param questionProperty
//     * @return
//     */
//    @PostMapping
//    public Result save(@RequestBody QuestionProperty questionProperty) {
//        boolean flag = questionPropertyService.save(questionProperty);
//        return new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag);
//    }


}
