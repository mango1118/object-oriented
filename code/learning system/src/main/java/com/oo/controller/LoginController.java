package com.oo.controller;

import com.oo.controller.R.Code;
import com.oo.controller.R.Result;
import com.oo.domain.LoginDTO;
import com.oo.domain.Student;
import com.oo.domain.Teacher;
import com.oo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:29
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginDTO loginDTO) {
        Object user = loginService.login(loginDTO);
//        System.out.println("ok");
        if (user instanceof Student) {
            return new Result(Code.STUDENT_LOGIN_OK, user);
        } else if (user instanceof Teacher) {
            return new Result(Code.TEACHER_LOGIN_OK, user);
        } else {
            return new Result(Code.POST_ERR, null, "账户密码错误，请重试！");
        }
    }

}
