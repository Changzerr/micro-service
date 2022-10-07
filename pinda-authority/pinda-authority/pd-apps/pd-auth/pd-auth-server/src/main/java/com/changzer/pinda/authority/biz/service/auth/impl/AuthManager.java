package com.changzer.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.changzer.pinda.authority.biz.service.auth.UserService;
import com.changzer.pinda.authority.dto.auth.LoginDTO;
import com.changzer.pinda.authority.dto.auth.UserDTO;
import com.changzer.pinda.authority.entity.auth.User;
import com.changzer.pinda.base.R;
import com.changzer.pinda.dozer.DozerUtils;
import com.changzer.pinda.exception.code.ExceptionCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */
@Service
public class AuthManager {
    @Autowired
    private UserService userService;
    @Autowired
    private DozerUtils dozerUtils;

    public R<LoginDTO> login(String account, String password) {
        //校验账号密码是否正确
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
        boolean check = check(user, password);
        if(!check){
            return R.fail(ExceptionCode.JWT_USER_INVALID);
        }
        UserDTO userDTO = dozerUtils.map(user, UserDTO.class);
        LoginDTO loginDTO = LoginDTO.builder().user(userDTO).build();
        return R.success(loginDTO);
        //为用户生成jwt令牌

        //将用户对应的权限（前端）缓存

        //将用户对应的权限（网关后端）缓存

        //封装返回结果


    }
    //校验账号密码是否正确
    private boolean check(User user, String password) {
        //密码加密
        String md5Hex = DigestUtils.md5Hex(password);
        if (user == null || !md5Hex.equals(password)) {
            return false;
        }
        return true;
    }
}
