package com.xcw.usercenter.service;
import java.util.Date;

import com.xcw.usercenter.model.domain.User;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  用户服务单元测试
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("testEddie");
        user.setUserAccount("123");
        user.setAvatarUrl("https://assets.leetcode.cn/aliyun-lc-upload/users/vigorous-jennings4mh/avatar_1703062532.png");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("123");
        user.setEmail("123");

        boolean res = userService.save(user);
        System.out.println(user.getId());
        assertTrue(res);
    }

    @Test
    void userRegister() {
        String userAccount = "testEddie";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount,userPassword,checkPassword);
        // 断言结果为-1
        Assertions.assertEquals(-1, result);

        // 账号长度小于4
        userAccount = "yu";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        // 断言结果为-1
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        // 断言结果为-1
        Assertions.assertEquals(-1, result);

        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        // 断言结果为-1
        Assertions.assertEquals(-1, result);

        checkPassword = "123456789";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        // 断言结果为-1
        Assertions.assertEquals(-1, result);

        userAccount = "dogYupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        // 断言结果为-1
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);

    }
}