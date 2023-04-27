package com.output.service.impl;

import com.output.common.Constants;
import com.output.common.ServiceResultEnum;
import com.output.controller.vo.UserVO;
import com.output.dao.UserMapper;
import com.output.entity.User;
import com.output.service.UserService;
import com.output.util.BeanUtil;
import com.output.util.MD5Util;
import com.output.util.OtherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String register(String loginName, String password) {
        if (userMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        User registerUser = new User();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if (userMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public UserVO login(String loginName, String passwordMD5) {
        User user = userMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null ) {
            if (user.getLockedFlag() == 1) {
//                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
                return null;
            }
            //昵称太长 影响页面展示
            if (user.getNickName() != null && user.getNickName().length() > 7) {
                String tempNickName = user.getNickName().substring(0, 7) + "..";
                user.setNickName(tempNickName);
            }
            UserVO newUserVO = new UserVO();
            BeanUtil.copyProperties(user, newUserVO);
            //设置购物车中的数量
//            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, newUserVO);
            return newUserVO;

        }
//        return ServiceResultEnum.LOGIN_ERROR.getResult();
        return null;
    }

    @Override
    public UserVO updateUserInfo(User userInfo) {
        User userFromDB = userMapper.selectByPrimaryKey(userInfo.getUserId());
        if (userFromDB != null) {
            if (StringUtils.hasText(userInfo.getNickName())) {
                userFromDB.setNickName(OtherUtils.cleanString(userInfo.getNickName()));
            }
            if (StringUtils.hasText(userInfo.getAddress())) {
                userFromDB.setAddress(OtherUtils.cleanString(userInfo.getAddress()));
            }
            if (StringUtils.hasText(userInfo.getIntroduceSign())) {
                userFromDB.setIntroduceSign(OtherUtils.cleanString(userInfo.getIntroduceSign()));
            }
            if (userMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                UserVO UserVO = new UserVO();
                BeanUtil.copyProperties(userFromDB, UserVO);
                return UserVO;
            }
        }
        return null;
    }
}
