package com.changzer.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.changzer.pinda.auth.server.utils.JwtTokenServerUtils;
import com.changzer.pinda.auth.utils.JwtUserInfo;
import com.changzer.pinda.auth.utils.Token;
import com.changzer.pinda.authority.biz.service.auth.ResourceService;
import com.changzer.pinda.authority.biz.service.auth.UserService;
import com.changzer.pinda.authority.dto.auth.LoginDTO;
import com.changzer.pinda.authority.dto.auth.ResourceQueryDTO;
import com.changzer.pinda.authority.dto.auth.UserDTO;
import com.changzer.pinda.authority.entity.auth.Resource;
import com.changzer.pinda.authority.entity.auth.User;
import com.changzer.pinda.base.R;
import com.changzer.pinda.common.constant.CacheKey;
import com.changzer.pinda.common.redis.RedisCache;
import com.changzer.pinda.dozer.DozerUtils;
import com.changzer.pinda.exception.code.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */
@Service
@Slf4j
public class AuthManager {
    @Autowired
    private UserService userService;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private JwtTokenServerUtils jwtTokenServerUtils;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RedisCache redisCache;

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

        //查询当前用户可以访问的资源权限
        List<String> permissionList = null;
        List<Resource> visibleResource = resourceService.findVisibleResource(ResourceQueryDTO.builder().userId(user.getId()).build());
        log.info("权限为：{}",visibleResource);
        if(visibleResource!=null && visibleResource.size() > 0){
            //将用户对应的权限（前端）缓存
            permissionList = visibleResource.stream().map(Resource::getCode).collect(Collectors.toList());

            //将用户对应的权限（网关后端）缓存
            List<String> canResource = visibleResource.stream().map((resource) -> {
                return resource.getMethod() + resource.getUrl();
            }).collect(Collectors.toList());
            redisCache.setCacheList(CacheKey.USER_RESOURCE+":"+ user.getId(),canResource);
            redisCache.expire(CacheKey.USER_RESOURCE+":"+ user.getId(),2,TimeUnit.HOURS);
            //cacheChannel.set(CacheKey.USER_RESOURCE, user.getId().toString(), canResource);
        }

        //封装返回结果
        LoginDTO loginDTO = LoginDTO.builder().user(userDTO)
                .token(token)
                .permissionsList(permissionList)
                .build();
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
        JwtUserInfo jwtUserInfo = new JwtUserInfo(user.getId(),user.getAccount(), user.getName(), user.getOrgId(), user.getStationId());
        return jwtTokenServerUtils.generateUserToken(jwtUserInfo, null);
    }
}
