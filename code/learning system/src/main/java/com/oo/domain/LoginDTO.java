package com.oo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/26 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    private String account;
    private String password;

}
