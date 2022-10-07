package com.changzer.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.changzer.pinda.auth.server.utils.JwtTokenServerUtils;
import com.changzer.pinda.auth.utils.JwtUserInfo;
import com.changzer.pinda.auth.utils.Token;
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
    @Autowired
    private JwtTokenServerUtils jwtTokenServerUtils;

    public R<LoginDTO> login(String account, String password) {
        //校验账号密码是否正确
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
        boolean check = check(user, password);
        if(!check){
            return R.fail(ExceptionCode.JWT_USER_INVALID);
        }
        UserDTO userDTO = dozerUtils.map(user, UserDTO.class);

        //为用户生成jwt令牌
        Token token = createToken(user);


        //将用户对应的权限（前端）缓存

        //将用户对应的权限（网关后端）缓存

        //封装返回结果
        LoginDTO loginDTO = LoginDTO.builder().user(userDTO).token(token).build();
        return R.success(loginDTO);

    }
    //校验账号密码是否正确
    private boolean check(User user, String password) {
        //密码加密
        String md5Hex = DigestUtils.md5Hex(password);
        return user != null && md5Hex.equals(user.getPassword());
    }

    //为用户生成jwt令牌
    private Token createToken(User user){
        JwtUserInfo jwtUserInfo = new JwtUserInfo(user.getId(),user.getAccount(), user.getName(), user.getOrgId(), user.getStationId())
        return jwtTokenServerUtils.generateUserToken(jwtUserInfo, null);
    }
}
