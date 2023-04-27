package com.output.service;


import com.output.controller.vo.UserVO;
import com.output.entity.User;

import javax.servlet.http.HttpSession;

public interface UserService {
    /**
     * 用户注册
     *
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);

    /**
     * 登录
     *
     * @param loginName
     * @param passwordMD5
     * @return
     */
    UserVO login(String loginName, String passwordMD5);

    UserVO updateUserInfo(User userInfo);
}
