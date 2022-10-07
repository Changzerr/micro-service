package com.changzer.test;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */
public class EasyCaptchaTest {
    public static void main(String[] args) throws FileNotFoundException {
        //算术类型图片验证码
        Captcha captcha = new ArithmeticCaptcha(115, 42);//指定图片的宽度和高度
        captcha.setCharType(2);
        captcha.out(new FileOutputStream(new File("d:\\hello.png")));
        String text = captcha.text();
        System.out.println(text);
    }
}
