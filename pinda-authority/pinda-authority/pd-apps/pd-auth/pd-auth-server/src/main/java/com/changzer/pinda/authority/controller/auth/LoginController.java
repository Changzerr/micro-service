package com.changzer.pinda.authority.controller.auth;

import com.changzer.pinda.authority.biz.service.auth.ResourceService;
import com.changzer.pinda.authority.biz.service.auth.ValidateCodeService;
import com.changzer.pinda.authority.biz.service.auth.impl.AuthManager;
import com.changzer.pinda.authority.dto.auth.LoginDTO;
import com.changzer.pinda.authority.dto.auth.LoginParamDTO;
import com.changzer.pinda.base.BaseController;
import com.changzer.pinda.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */

@RestController
@RequestMapping("/anno")
@Api(tags="登录控制器", value = "LoginController")
public class LoginController extends BaseController {

    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private AuthManager authManager;



    @ApiOperation(notes = "验证码", value="验证码")
    @GetMapping(value = "/captcha", produces = "image/png")
    public void captcha(@RequestParam("key") String key, HttpServletResponse response) throws IOException {
        validateCodeService.create(key,response);
    }

    @ApiOperation(notes = "登录", value="登录")
    @PostMapping("/login")
    public R<LoginDTO> login(@Validated @RequestBody LoginParamDTO loginParamDTO){
        //校验验证码
        boolean check = validateCodeService.check(loginParamDTO.getKey(), loginParamDTO.getCode());
        if(check){
            //验证码校验成功
            return authManager.login(loginParamDTO.getAccount(), loginParamDTO.getPassword());
        }
        //验证码校验失败
        return this.success(null);
    }


    @ApiOperation(notes = "校验验证码", value="校验验证码")
    @PostMapping("/check")
    public boolean check(@RequestBody LoginParamDTO loginParamDTO){
        return validateCodeService.check(loginParamDTO.getKey(),loginParamDTO.getCode());
    }
}
