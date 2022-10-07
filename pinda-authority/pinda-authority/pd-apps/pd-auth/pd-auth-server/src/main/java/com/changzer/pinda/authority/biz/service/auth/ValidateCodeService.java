package com.changzer.pinda.authority.biz.service.auth;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */
public interface ValidateCodeService {
    /**
     * 生成验证码
     */
    void create(String key, HttpServletResponse response) throws IOException;

    boolean check(String key, String code);
}
