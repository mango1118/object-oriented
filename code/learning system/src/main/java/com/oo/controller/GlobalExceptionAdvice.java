package com.oo.controller;

import com.oo.controller.R.Result;
import com.oo.exception.BusinessException;

import com.oo.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/28 11:10
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex){
        System.out.println("捕获系统异常：" + ex.getMessage());
        return new Result(ex.getCode(), null, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex){
        System.out.println("捕获业务异常：" + ex.getMessage());
        return new Result(ex.getCode(), null, ex.getMessage());
    }
}
