package com.xcw.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xcw.usercenter.service.UserService;
import com.xcw.usercenter.model.domain.User;
import com.xcw.usercenter.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * 用户服务实现类
 *
* @author 20339
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-04-25 20:45:37
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验
        //引入类库，避免一个个判断是否为空！（hutools  or commons-lang3）
        //此处引入了commons-lang3

        //如果有一个为空，则返回-1
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            return -1;
        }
        //如果账号长度小于4，则返回-1
        if(userAccount.length() < 4){
            return -1;
        }
        //如果密码长度小于8，则返回-1
        if(userPassword.length() < 8){
            return -1;
        }
        //账户不能包含特殊字符（正则表达式）
        //如果账户包含特殊字符，则返回-1
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]";
        if(userAccount.matches(regEx)){
            return -1;
        }
        //如果密码和确认密码不一致，则返回-1
        if(!userPassword.equals(checkPassword)){
            return -1;
        }

        /* 由于其中使用到了QueryWrapper ， 查询了数据库，故将其后置，
        * 若不符合条件则提前返回，不会造成不必要的资源浪费！
        * */
        //用户账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count > 0){
            //如果用户账户已经存在，则返回-1
            return -1;
        }

        //2. 加密
        //加密密码
        // 使用MD5对密码进行加密
        final String SAlt = "xcw";
        String encryptedPassword = DigestUtils.md5DigestAsHex((SAlt + "mypassword").getBytes());
        System.out.println(encryptedPassword);
        //3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        boolean res = this.save(user);
        //如果插入失败，则返回-1
        if(!res){
            return -1;
        }

        //4. 返回新用户id
        return user.getId();
    }
}




