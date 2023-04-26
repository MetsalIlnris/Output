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
     * @param httpSession
     * @return
     */
    UserVO login(String loginName, String passwordMD5, HttpSession httpSession);

    UserVO updateUserInfo(User userInfo);
}
