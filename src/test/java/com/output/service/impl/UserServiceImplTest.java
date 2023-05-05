package com.output.service.impl;

import com.output.OutputApplication;
import com.output.dao.UserMapper;
import com.output.entity.User;
import com.output.service.UserService;
import com.output.util.MD5Util;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OutputApplication.class)
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Test
    void register() {
        userService.register("userTest","12345");
        Assert.assertEquals(userMapper.selectByLoginName("userTest").getLoginName(),"userTest");
        Assert.assertEquals(userMapper.selectByLoginName("userTest").getPasswordMd5(), MD5Util.MD5Encode("12345", "UTF-8"));
    }

    @Test
    void login() {
        userService.register("userTest","12345");
        Assert.assertEquals(userMapper.selectByLoginName("userTest").getLoginName(),"userTest");
        Assert.assertEquals(userMapper.selectByLoginName("userTest").getPasswordMd5(), MD5Util.MD5Encode("12345", "UTF-8"));
    }

    @Test
    void updateUserInfo() {
        String preName = userMapper.selectByPrimaryKey((long) 1).getLoginName();
        User userInfo = new User();
        userInfo.setUserId((long) 1);
        userInfo.setAddress("中国地质大学");
        userService.updateUserInfo(userInfo);
        Assert.assertEquals(userMapper.selectByPrimaryKey((long) 1).getLoginName(),preName);
        Assert.assertEquals(userMapper.selectByPrimaryKey((long) 1).getAddress(),"中国地质大学");
    }
}