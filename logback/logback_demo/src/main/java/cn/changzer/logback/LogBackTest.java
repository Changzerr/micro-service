package cn.changzer.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lingqu
 * @date 2022/10/5
 * @apiNote
 */
public class LogBackTest {

    @Test
    public void test1(){
        Logger logger = LoggerFactory.getLogger("cn.changzer.logback.HelloWorld");
        //默认debug级别
        logger.trace("trace ...");
        logger.debug("debug ...");
        logger.info("info ...");
        logger.warn("warn ...");
        logger.error("error ...");
    }
}
