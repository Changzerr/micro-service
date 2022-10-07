package com.changzer.test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.changzer.pinda.AuthorityApplication;
import com.changzer.pinda.authority.biz.service.auth.UserService;
import com.changzer.pinda.authority.entity.auth.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthorityApplication.class)
public class MysqlTest {
    @Autowired
    UserService userService;

    @Test
    public void test(){
        User pinda = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, "pinda"));

        System.out.println("==============="+pinda);
        System.out.println("==============="+pinda);
        System.out.println("==============="+pinda);
    }
}
