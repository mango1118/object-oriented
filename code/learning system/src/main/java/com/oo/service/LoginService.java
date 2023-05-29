package com.oo.service;

import com.oo.domain.LoginDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 16:06
 */
@Transactional
public interface LoginService {

    /**
     * 登录
     *
     * @param loginDTO
     * @return Object 用户身份
     */
    Object login(LoginDTO loginDTO);

}
