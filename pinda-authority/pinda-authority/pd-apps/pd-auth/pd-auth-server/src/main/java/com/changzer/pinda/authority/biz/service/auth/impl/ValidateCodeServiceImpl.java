package com.changzer.pinda.authority.biz.service.auth.impl;

import com.changzer.pinda.authority.biz.service.auth.ValidateCodeService;
import com.changzer.pinda.common.constant.CacheKey;
import com.changzer.pinda.exception.BizException;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Autowired
    private CacheChannel cache;

    @Override
    public void create(String key, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(key)) {
            throw BizException.validFail("验证码key不能为空");
        }

        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);

        Captcha captcha = new ArithmeticCaptcha(115, 42);
        captcha.setCharType(2);

        cache.set(CacheKey.CAPTCHA, key, StringUtils.lowerCase(captcha.text()));
        captcha.out(response.getOutputStream());
    }

    @Override
    public boolean check(String key, String code) {
        if(StringUtils.isEmpty(key)){
            throw BizException.validFail("验证码key不能为空");
        }

        //根据key从缓存中获取验证码
        CacheObject cacheObject = cache.get(CacheKey.CAPTCHA, key);
        if (cacheObject.getValue() == null) {
            throw BizException.validFail("验证码已经过期");
        }

        //比对验证码
        String codeInCache = (String) cacheObject.getValue();
        if(!codeInCache.equals(code)){
            throw BizException.validFail("验证码错误");
        }

        //校验成功，清除验证码
        cache.evict(CacheKey.CAPTCHA, key);
        return true;
    }
}
