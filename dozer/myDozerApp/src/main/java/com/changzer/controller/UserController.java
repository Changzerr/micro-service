package com.changzer.controller;

import com.changzer.dto.UserDTO;
import com.changzer.entity.UserEntity;
import com.itheima.pinda.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lingqu
 * @date 2022/9/30
 * @apiNote
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private DozerUtils dozerUtils; //在pd-tools-dozer中已经完成了自动配置，可以直接注入

    @GetMapping("/mapper")
    public UserEntity mapper(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("10");
        userDTO.setUserName("itcast");
        userDTO.setUserAge(20);
        userDTO.setAddress("；大苏打");
        userDTO.setBirthday("2001-08-01");

        UserEntity userEntity = dozerUtils.map(userDTO, UserEntity.class);
        return userEntity;
    }
}
