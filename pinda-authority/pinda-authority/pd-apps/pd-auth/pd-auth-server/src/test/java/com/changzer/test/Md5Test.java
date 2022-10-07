package com.changzer.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */
public class Md5Test {
    @Test
    public void test(){
        String md5Hex = DigestUtils.md5Hex("pinda123");
        System.out.println(md5Hex);

    }
}
